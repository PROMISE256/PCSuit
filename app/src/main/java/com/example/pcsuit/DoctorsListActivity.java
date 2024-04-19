package com.example.pcsuit;

import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class DoctorsListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDoctors;
    private DoctorAdapter doctorAdapter;
    private ArrayList<Doc> doctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_list);

        // Initialize RecyclerView and its layout manager
        recyclerViewDoctors = findViewById(R.id.doctors_recycler_view);
        recyclerViewDoctors.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list of doctors
        doctorList = new ArrayList<>();

        // Initialize Firebase database reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");

        // Get selected hospital ID from intent
        String selectedHospitalId = getIntent().getStringExtra("hospitalId");

        // Query doctors under the selected hospital ID
        Query query = databaseReference.orderByChild("hospitalId").equalTo(selectedHospitalId);

        // Add ValueEventListener to retrieve doctors data
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Doc doctor = snapshot.getValue(Doc.class);
                    doctorList.add(doctor);
                }
                // Initialize and set up the adapter for the RecyclerView
                doctorAdapter = new DoctorAdapter(DoctorsListActivity.this, doctorList);
                recyclerViewDoctors.setAdapter(doctorAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(DoctorsListActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
