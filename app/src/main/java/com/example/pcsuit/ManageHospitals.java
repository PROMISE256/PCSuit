package com.example.pcsuit;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ManageHospitals extends AppCompatActivity {

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_hospitals);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("hospitals");

        // Initialize views
        AutoCompleteTextView autoCompleteHospital = findViewById(R.id.autoCompleteHospital);
        TextInputEditText editTextHospitalLocation = findViewById(R.id.editTextHospitalLocation);
        TextInputEditText editTextEmergencyContact = findViewById(R.id.editTextEmergencyContact);
        TextInputEditText briefDescription = findViewById(R.id.briefDescription);
        Button buttonAddHospital = findViewById(R.id.buttonAddHospital);

        // Initialize hospitals array
        String[] hospitals = getResources().getStringArray(R.array.hospitals);
        ArrayAdapter<String> hospitalAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, hospitals);
        autoCompleteHospital.setAdapter(hospitalAdapter);

        // Set click listener for Add Hospital button
        buttonAddHospital.setOnClickListener(v -> {
            // Get values from input fields
            String hospital = autoCompleteHospital.getText().toString().trim();
            String hospitalLocation = Objects.requireNonNull(editTextHospitalLocation.getText()).toString().trim();
            String emergencyContact = Objects.requireNonNull(editTextEmergencyContact.getText()).toString().trim();
            String description = Objects.requireNonNull(briefDescription.getText()).toString().trim();

            // Validate input fields
            if (hospital.isEmpty() || hospitalLocation.isEmpty() || emergencyContact.isEmpty() || description.isEmpty()) {
                Toast.makeText(ManageHospitals.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get current user's ID
            String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            // Create Hospital object
            Hospital hospital1 = new Hospital(userId, hospital, hospitalLocation, emergencyContact, description);

            // Push hospital object to Firebase database
            databaseReference.push().setValue(hospital1)
                    .addOnSuccessListener(aVoid -> {
                        // Show success message
                        Toast.makeText(ManageHospitals.this, "Hospital added successfully", Toast.LENGTH_SHORT).show();

                        // Clear input fields
                        autoCompleteHospital.setText("");
                        editTextHospitalLocation.setText("");
                        editTextEmergencyContact.setText("");
                        briefDescription.setText("");
                    })
                    .addOnFailureListener(e -> {
                        // Show failure message
                        Toast.makeText(ManageHospitals.this, "Failed to add hospital: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }



    // Define Hospital class
    private static class Hospital {

        private final String userId;
        private final String name;
        private final String location;
        private final String emergencyContact;
        private String description;

        // Constructor
        public Hospital(String userId, String name, String location, String emergencyContact, String description) {
            this.userId = userId;
            this.name = name;
            this.location = location;
            this.emergencyContact = emergencyContact;
            this.description = description;
        }

        public String getUserId() {
            return userId;
        }

        public String getName() {
            return name;
        }

        public String getEmergencyContact() {
            return emergencyContact;
        }

        public String getLocation() {
            return location;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        // Getter and setter methods
        // (You can generate these using your IDE or manually implement them)
    }
}
