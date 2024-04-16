package com.example.pcsuit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientHome extends AppCompatActivity {

    private TextView textViewName, textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        textViewName = findViewById(R.id.textView);
        textViewEmail = findViewById(R.id.textView2);
        Button btnHospital = findViewById(R.id.btn_hospital);
        Button btnAppointments = findViewById(R.id.btn_appointments);
        Button btnPayments = findViewById(R.id.btn_payments);
        Button btnLogout = findViewById(R.id.logout_button);

        // Get current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Reference to the user node
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

            // Listener to fetch user data
            usersRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);

                        // Set retrieved data to TextViews
                        textViewName.setText(name);
                        textViewEmail.setText(email);
                    } else {
                        Log.d("PatientHome", "No such user exists");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("PatientHome", "Failed to retrieve user data: " + databaseError.getMessage());
                }
            });
        }

        // Button click listeners
        btnHospital.setOnClickListener(v -> startActivity(new Intent(PatientHome.this, Hospital.class)));

        btnAppointments.setOnClickListener(v -> startActivity(new Intent(PatientHome.this, ManageHospitals.class)));

        btnPayments.setOnClickListener(v -> startActivity(new Intent(PatientHome.this, ManagePayments.class)));

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(PatientHome.this, PatieLogin.class));
            finish();
        });
    }
}
