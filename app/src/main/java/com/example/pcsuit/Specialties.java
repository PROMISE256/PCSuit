package com.example.pcsuit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Specialties extends AppCompatActivity {

    private Button buttonPatientLogin, buttonDoctorLogin;
    private TextView textViewAdminUse;
    private int adminClickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPatientLogin = findViewById(R.id.buttonPatientLogin);
        buttonDoctorLogin = findViewById(R.id.buttonDoctorLogin);
        textViewAdminUse = findViewById(R.id.textViewAdminUse);

        buttonPatientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Patient Login button click
                startActivity(new Intent(Specialties.this, PatieLogin.class));
            }
        });

        buttonDoctorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Doctor Login button click
                startActivity(new Intent(Specialties.this, DoctorLogin.class));
            }
        });

        textViewAdminUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminClickCount++;
                if (adminClickCount >= 7) {
                    // Reset the click count
                    adminClickCount = 0;
                    // Navigate to the Admin page
                    startActivity(new Intent(Specialties.this, AdminActivity.class));
                } else {
                    Toast.makeText(Specialties.this, "Admin Use only", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
