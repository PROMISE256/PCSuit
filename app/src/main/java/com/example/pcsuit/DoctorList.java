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

public class DoctorList extends AppCompatActivity {

    RecyclerView recyclerViewDoctors;
    EditText editTextSearch;
    DoctorAdapter doctorAdapter;
    ArrayList<Doc> doctorData;
    DatabaseReference databaseReference;
    String selectedHospitalName; // Name of the selected hospital

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        // Get the selected hospital name from the Intent
        selectedHospitalName = getIntent().getStringExtra("selectedHospitalName");

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");

        // Initialize views
        recyclerViewDoctors = findViewById(R.id.doctors_recycler_view);
        recyclerViewDoctors.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDoctors.setHasFixedSize(true);

        editTextSearch = findViewById(R.id.editTextSearch);

        doctorData = new ArrayList<>();

        // Set up RecyclerView
        doctorAdapter = new DoctorAdapter(this, doctorData);
        recyclerViewDoctors.setAdapter(doctorAdapter);

        // Implement search functionality
        editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                doctorAdapter.getFilter().filter("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doctorAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        // Read doctor data from Firebase
        readDoctorData();
    }

    private void readDoctorData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctorData.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Doc doctor = snapshot.getValue(Doc.class);
                    if (doctor != null && doctor.getHospital().equals(selectedHospitalName)) {
                        doctorData.add(doctor);
                    }
                }
                doctorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
}
