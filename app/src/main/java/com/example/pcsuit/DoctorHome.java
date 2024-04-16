package com.example.pcsuit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class DoctorHome extends AppCompatActivity {

    private static final int REQUEST_CODE_GALLERY = 101;

    private EditText editText;
    private String stringDateSelected = "";
    private DatabaseReference databaseReference;
    private ImageView profilePictureImageView;
    private TextView doctorNameTextView;
    private TextView specialtyTextView;
    private TextView doctorEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        // Initialize views
        doctorNameTextView = findViewById(R.id.doctor_name);
        specialtyTextView = findViewById(R.id.specialty);
        doctorEmailTextView = findViewById(R.id.doctorEmail);
        editText = findViewById(R.id.editText);
        profilePictureImageView = findViewById(R.id.profile_picture);

        // Initialize Firebase components
        FirebaseAuth auth = FirebaseAuth.getInstance(); // Initialize for user authentication
        databaseReference = FirebaseDatabase.getInstance().getReference("Doctors")
                .child(Objects.requireNonNull(auth.getCurrentUser()).getUid()); // Use current user's UID as node key
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        // Load doctor information
        loadDoctorInformation();

        // Load profile picture
        loadProfilePicture();

        // Setup CalendarView
        CalendarView calendarView1 = findViewById(R.id.calendarView);
        calendarView1.setOnDateChangeListener((calendarView, i, i1, i2) -> {
            stringDateSelected = i + "-" + (i1 + 1) + "-" + i2;
            calendarClicked();
        });
    }




    private void loadDoctorInformation() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Doc doc = dataSnapshot.getValue(Doc.class);
                    if (doc != null) {
                        doctorNameTextView.setText(doc.getName());
                        specialtyTextView.setText(doc.getSpeciality());
                        doctorEmailTextView.setText(doc.getEmail());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void uploadProfilePicture() {
        databaseReference.child("profilePictureURL").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String profilePictureURL = dataSnapshot.getValue(String.class);
                    // Load profile picture using Glide or Picasso or any other library
                    if (profilePictureURL != null) {
                        Glide.with(DoctorHome.this)
                                .load(profilePictureURL)
                                .into(profilePictureImageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void calendarClicked() {
        databaseReference.child("events").child(stringDateSelected).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    editText.setText(snapshot.getValue().toString());
                } else {
                    editText.setText("No event");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    public void buttonSaveEvent(View view) {
        String eventText = editText.getText().toString();
        if (!eventText.isEmpty()) {
            databaseReference.child("events").child(stringDateSelected).setValue(eventText);
            editText.setText("");
             Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            profilePictureImageView.setImageURI(imageUri);
            uploadProfilePicture();
        }
    }

    private void loadProfilePicture() {
        databaseReference.child("profilePictureURL").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String profilePictureURL = dataSnapshot.getValue(String.class);
                    // Load profile picture using Glide
                    if (profilePictureURL != null) {
                        Glide.with(DoctorHome.this)
                                .load(profilePictureURL)
                                .into(profilePictureImageView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
