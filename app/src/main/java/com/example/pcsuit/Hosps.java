package com.example.pcsuit;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Hosps extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosps);

        // Initialize buttons
        Button hospital1Button = findViewById(R.id.hospital1_button);
        Button hospital2Button = findViewById(R.id.hospital2_button);
        Button hospital3Button = findViewById(R.id.hospital3_button);
        Button hospital4Button = findViewById(R.id.hospital4_button);
        Button hospital5Button = findViewById(R.id.hospital5_button);
        Button hospital6Button = findViewById(R.id.hospital6_button);
        Button hospital7Button = findViewById(R.id.hospital7_button);
        Button hospital8Button = findViewById(R.id.hospital8_button);
        Button hospital9Button = findViewById(R.id.hospital9_button);
        Button hospital10Button = findViewById(R.id.hospital10_button);
        Button hospital11Button = findViewById(R.id.hospital11_button);
        Button hospital12Button = findViewById(R.id.hospital12_button);

        // Set click listeners for all hospital buttons
        hospital1Button.setOnClickListener(v -> openDoctorsListActivity());
        hospital2Button.setOnClickListener(v -> openDoctorsListActivity());
        hospital3Button.setOnClickListener(v -> openDoctorsListActivity());
        hospital4Button.setOnClickListener(v -> openDoctorsListActivity());
        hospital5Button.setOnClickListener(v -> openDoctorsListActivity());
        hospital6Button.setOnClickListener(v -> openDoctorsListActivity());
        hospital7Button.setOnClickListener(v -> openDoctorsListActivity());
        hospital8Button.setOnClickListener(v -> openDoctorsListActivity());
        hospital9Button.setOnClickListener(v -> openDoctorsListActivity());
        hospital10Button.setOnClickListener(v -> openDoctorsListActivity());
        hospital11Button.setOnClickListener(v -> openDoctorsListActivity());
        hospital12Button.setOnClickListener(v -> openDoctorsListActivity());
    }

    // Method to open DoctorsListActivity
    private void openDoctorsListActivity() {
        Intent intent = new Intent(Hosps.this, DoctorList.class);
        startActivity(intent);
    }
}
