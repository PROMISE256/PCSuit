package com.example.pcsuit;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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
import java.util.List;

public class DoctorList extends AppCompatActivity {

    private DoctorAdapter doctorAdapter;
    private ArrayList<Doc> doctorList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        // Initialize views
        RecyclerView recyclerView = findViewById(R.id.doctors_recycler_view);

        // Initialize doctor list
        doctorList = new ArrayList<>();

        // Set up RecyclerView
        doctorAdapter = new DoctorAdapter(this, doctorList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(doctorAdapter);

        // Set up back button
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        // Load doctors data
        loadDoctorsData();

        // Set up hospital buttons click listeners
        setHospitalButtonClickListeners();
    }

    private void loadDoctorsData() {
        // Get reference to the doctors node in Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");

        // Add ValueEventListener to fetch data
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctorList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Convert the DataSnapshot to a Doctor object and add it to the list
                    Doc doc = snapshot.getValue(Doc.class);
                    doctorList.add(doc);
                }
                // Notify the adapter that the data set has changed
                doctorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(DoctorList.this, "Failed to load doctors data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setHospitalButtonClickListeners() {
        // Find all hospital buttons
        List<Button> hospitalButtons = new ArrayList<>();
        hospitalButtons.add(findViewById(R.id.hospital1_button));
        hospitalButtons.add(findViewById(R.id.hospital2_button));
        hospitalButtons.add(findViewById(R.id.hospital3_button));
        hospitalButtons.add(findViewById(R.id.hospital4_button));
        hospitalButtons.add(findViewById(R.id.hospital5_button));
        hospitalButtons.add(findViewById(R.id.hospital6_button));
        hospitalButtons.add(findViewById(R.id.hospital7_button));
        hospitalButtons.add(findViewById(R.id.hospital8_button));
        hospitalButtons.add(findViewById(R.id.hospital9_button));
        hospitalButtons.add(findViewById(R.id.hospital10_button));
        hospitalButtons.add(findViewById(R.id.hospital11_button));
        hospitalButtons.add(findViewById(R.id.hospital12_button));
        // Add more hospital buttons as needed

        // Set click listeners for each hospital button
        for (final Button hospitalButton : hospitalButtons) {
            hospitalButton.setOnClickListener(v -> {
                // Get the hospital name from the button text
                String hospital = hospitalButton.getText().toString();
                // Retrieve doctors for the selected hospital
                retrieveDoctorsByHospital(hospital);
            });
        }
    }

    private void retrieveDoctorsByHospital(final String hospitalName) {
        // Get reference to the doctors node in Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");

        // Add ValueEventListener to fetch data for the selected hospital
        databaseReference.orderByChild("hospital").equalTo(hospitalName).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctorList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Convert the DataSnapshot to a Doctor object and add it to the list
                    Doc doc = snapshot.getValue(Doc.class);
                    doctorList.add(doc);
                }
                // Notify the adapter that the data set has changed
                doctorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(DoctorList.this, "Failed to retrieve doctors for " + hospitalName, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
