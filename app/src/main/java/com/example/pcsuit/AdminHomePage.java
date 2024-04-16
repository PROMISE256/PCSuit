package com.example.pcsuit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        CardView cardViewManageDoctors = findViewById(R.id.cardViewManageDoctors);
        CardView cardViewManagePatients = findViewById(R.id.cardViewManagePatients);
        CardView cardViewManageHospitals = findViewById(R.id.cardViewManageHospitals);
        CardView cardViewOtherTasks = findViewById(R.id.cardViewOtherTasks);

        // Set click listeners for card views
        cardViewManageDoctors.setOnClickListener(v -> {
            // Navigate to Manage Doctors activity
            startActivity(new Intent(AdminHomePage.this, ManageDoctors.class));
        });

        cardViewManagePatients.setOnClickListener(v -> {
            // Navigate to Manage Patients activity
            startActivity(new Intent(AdminHomePage.this, ManagePatients.class));
        });

        cardViewManageHospitals.setOnClickListener(v -> {
            // Navigate to Manage Appointments activity
            startActivity(new Intent(AdminHomePage.this, ManageHospitals.class));
        });

        cardViewOtherTasks.setOnClickListener(v -> {
            // Log out or perform other administrative tasks
            // For demonstration, let's just finish the current activity
            finish();
        });
    }
}
