package com.example.pcsuit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignIn extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTextName, editTextEmail, editTextPassword, editTextConfirmPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private StorageReference storageReference;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        storageReference = FirebaseStorage.getInstance().getReference("profile_images");

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        Button buttonSignUp = findViewById(R.id.buttonSignUp);
        Button buttonChoosePicture = findViewById(R.id.buttonChoosePicture);
        TextView textViewLogin = findViewById(R.id.textViewLogin);

        buttonChoosePicture.setOnClickListener(v -> openFileChooser());

        buttonSignUp.setOnClickListener(v -> signUpUser());

        textViewLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignIn.this, PatieLogin.class));
            finish();
        });
    }

    private void signUpUser() {
        final String name = editTextName.getText().toString();
        final String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign up success, update UI
                        Toast.makeText(SignIn.this, "Sign up successful", Toast.LENGTH_SHORT).show();

                        // Upload profile image if available
                        if (imageUri != null) {
                            uploadImageAndSaveUserInfo(name, email, password);
                        } else {
                            saveUserInfo(name, email, password, "");
                        }

                        // You can navigate to another activity or perform other actions here
                    } else {
                        // Sign up failed, display a message to the user
                        Log.w("SignupActivity", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(SignIn.this, "Sign up failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadImageAndSaveUserInfo(final String name, final String email, final String password) {
        StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

        fileReference.putFile(imageUri)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(uriTask -> {
                            if (uriTask.isSuccessful()) {
                                String imageUrl = uriTask.getResult().toString();
                                saveUserInfo(name, email, password, imageUrl);
                            } else {
                                Log.e("SignIn", "Failed to get download URL", uriTask.getException());
                                Toast.makeText(SignIn.this, "Failed to upload profile picture", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Log.e("SignIn", "uploadImageAndSaveUserInfo:failure", task.getException());
                        Toast.makeText(SignIn.this, "Failed to upload profile picture", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserInfo(String name, String email, String password, String profileImageUrl) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("password", password);
        user.put("profileImageUrl", profileImageUrl);

        usersRef.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("SignIn", "User info saved successfully");
                        // Navigate to your desired activity upon successful signup
                    } else {
                        Log.e("SignIn", "Error adding document", task.getException());
                        Toast.makeText(SignIn.this, "Failed to save user info", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            // You can display the selected image if needed
        }
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
    }
}

