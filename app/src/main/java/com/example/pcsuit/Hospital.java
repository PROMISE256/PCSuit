package com.example.pcsuit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Hospital extends AppCompatActivity {

    RecyclerView recyclerViewHospitals;
    EditText editTextSearch;
    HospitalAdapter hospitalAdapter;
    ArrayList<HospitalData> hospitalData;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("hospitals");

        // Initialize views
        recyclerViewHospitals = findViewById(R.id.recyclerViewHospitals);
        recyclerViewHospitals.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHospitals.setHasFixedSize(true);

        editTextSearch = findViewById(R.id.editTextSearch);

        hospitalData = new ArrayList<>();

        // Set up RecyclerView
        hospitalAdapter = new HospitalAdapter(this, hospitalData);
        recyclerViewHospitals.setAdapter(hospitalAdapter);

        // Read hospital data from Firebase
        readHospitalData();

        // Implement search functionality
        editTextSearch.addTextChangedListener(new TextWatcher() {
            private Editable s;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hospitalAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

                this.s = s;
            }

        });
    }

    private void readHospitalData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hospitalData.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    HospitalData hospital = snapshot.getValue(HospitalData.class);
                    hospitalData.add(hospital);

                }
                hospitalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
}
