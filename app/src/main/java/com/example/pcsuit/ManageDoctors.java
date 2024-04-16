package com.example.pcsuit;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ManageDoctors extends AppCompatActivity {

    private TextInputEditText editTextName, editTextAge, editTextExperience, editTextEvents,
            editTextLanguages, editTextHospitalLocation, editTextDescription, editTextEmail, editTextPassword;
    private AutoCompleteTextView autoCompleteHospital;
    private RadioGroup radioGroupGender;
    private Spinner spinnerSpeciality;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctors);

        mAuth = FirebaseAuth.getInstance();
        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Doctors");

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextEvents = findViewById(R.id.editTextEvents);
        editTextExperience = findViewById(R.id.editTextExperience);
        spinnerSpeciality = findViewById(R.id.spinnerSpeciality);
        editTextLanguages = findViewById(R.id.editTextLanguages);
        editTextHospitalLocation = findViewById(R.id.editTextHospitalLocation);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        autoCompleteHospital = findViewById(R.id.autoCompleteHospital);

        // Set up the autocomplete dropdown for hospitals
        String[] hospitals = getResources().getStringArray(R.array.hospitals);
        ArrayAdapter<String> hospitalAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, hospitals);
        autoCompleteHospital.setAdapter(hospitalAdapter);

        Button buttonAddDoctor = findViewById(R.id.buttonAddDoctor);

        // Set click listener for Add Doctor button
        buttonAddDoctor.setOnClickListener(v -> addDoctor());
    }

    // Method to add doctor to Firebase database
    private void addDoctor() {
        // Get values from input fields
        String name = Objects.requireNonNull(editTextName.getText()).toString().trim();
        String ageString = Objects.requireNonNull(editTextAge.getText()).toString().trim();
        String experience = Objects.requireNonNull(editTextExperience.getText()).toString().trim();
       // String events = Objects.requireNonNull(editTextEvents.getText()).toString().trim();
        String speciality = spinnerSpeciality.getSelectedItem().toString().trim();
        String languages = Objects.requireNonNull(editTextLanguages.getText()).toString().trim();
        String hospital = autoCompleteHospital.getText().toString().trim();
        String hospitalLocation = Objects.requireNonNull(editTextHospitalLocation.getText()).toString().trim();
        String description = Objects.requireNonNull(editTextDescription.getText()).toString().trim();
        String email = Objects.requireNonNull(editTextEmail.getText()).toString().trim();
        String password = Objects.requireNonNull(editTextPassword.getText()).toString().trim();

        // Validate input fields
        if (name.isEmpty() || ageString.isEmpty() || experience.isEmpty() || languages.isEmpty() || hospital.isEmpty() || hospitalLocation.isEmpty() || description.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = 0;
        try {
            age = Integer.parseInt(ageString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid age format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if a gender is selected
        int selectedRadioButtonId = radioGroupGender.getCheckedRadioButtonId();
        if (selectedRadioButtonId == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();
            return;
        }
        String gender = ((RadioButton) findViewById(selectedRadioButtonId)).getText().toString();

        int finalAge = age;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Push doctor object to Firebase database with auto-generated user ID
                        DatabaseReference doctorRef = databaseReference.push();
                        String userId = Objects.requireNonNull(user).getUid(); // Get the auto-generated user ID
                        doctorRef.child("userId").setValue(userId);
                        doctorRef.child("name").setValue(name);
                        doctorRef.child("age").setValue(finalAge);
                        doctorRef.child("gender").setValue(gender);
                        //doctorRef.child("events").setValue(events);
                        doctorRef.child("experience").setValue(experience);
                        doctorRef.child("speciality").setValue(speciality);
                        doctorRef.child("languages").setValue(languages);
                        doctorRef.child("hospital").setValue(hospital);
                        doctorRef.child("hospitalLocation").setValue(hospitalLocation);
                        doctorRef.child("description").setValue(description);
                        doctorRef.child("email").setValue(email)
                                .addOnSuccessListener(aVoid -> {
                                    // Show success message
                                    Toast.makeText(this, "Doctor added successfully", Toast.LENGTH_SHORT).show();
                                    // Clear input fields
                                    clearFields();
                                })
                                .addOnFailureListener(e -> {
                                    // Show failure message
                                    Toast.makeText(this, "Failed to add doctor: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // If sign in fails, display a message to the user.
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText(ManageDoctors.this, "Email is already registered", Toast.LENGTH_SHORT).show();
                        } else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(ManageDoctors.this, "Invalid email or password format", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ManageDoctors.this, "Failed to create account: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void clearFields() {
        editTextName.setText("");
        editTextAge.setText("");
        editTextExperience.setText("");
        editTextEvents.setText("");
        spinnerSpeciality.setSelection(0); // Reset spinner to the first item
        editTextLanguages.setText("");
        editTextHospitalLocation.setText("");
        editTextDescription.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");
        radioGroupGender.clearCheck();
        autoCompleteHospital.setText("");
    }
}
