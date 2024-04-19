package com.example.pcsuit;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorProfileActivity extends AppCompatActivity {

    private TextView titleName, titleEmail, specialty, hospital, languages, events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
/*
            // Find views by their IDs
            // Views in the layout
            //ImageView profileImg = findViewById(R.id.profileImg);
            titleName = findViewById(R.id.titleName);
            titleEmail = findViewById(R.id.titleEmail);
            specialty = findViewById(R.id.specialty_doctor);
            hospital = findViewById(R.id.hospital);
            languages = findViewById(R.id.languages);
            events = findViewById(R.id.available_days);
            Button requestedAppointment = findViewById(R.id.requested_appointment);

            // Retrieve doctor ID from intent extra
            String userId = getIntent().getStringExtra("selectedUserId");

            // Query Firebase database to retrieve doctor's profile data based on doctorId
            DatabaseReference doctorRef;
        assert userId != null;
        doctorRef = FirebaseDatabase.getInstance().getReference("Doctors").child(userId);
            doctorRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // Retrieve doctor's profile data
                    DoctorData doctor = dataSnapshot.getValue(DoctorData.class);

                    // Populate UI with doctor's profile data
                    if (doctor != null) {
                        populateDoctorProfile(doctor);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                }
            });

            // Handle request appointment button click if needed
            requestedAppointment.setOnClickListener(v -> {
                // Implement request appointment functionality
            });
        }

        private void populateDoctorProfile(DoctorData doctor) {
            // Populate UI elements with doctor's profile data
            titleName.setText(doctor.getName());
            titleEmail.setText(doctor.getEmail());
            specialty.setText(doctor.getSpecialty());
            hospital.setText(doctor.getHospital());
            languages.setText(doctor.getLanguages());
            events.setText(doctor.getEvents());
        }*/
    }
}