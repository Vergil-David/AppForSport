package com.example.befit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.befit.R;
import com.example.befit.activities.RegDataActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegActivity extends AppCompatActivity {
    private static final int MIN_PASSWORD_LENGTH = 6;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    private EditText emailEdit;
    private EditText passwordEdit;
    private EditText nameEdit;
    private EditText ageEdit;
    private EditText weightEdit;
    private MaterialButton signupBtn;
    private MaterialButton logInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        initializeFirebase();
        initializeViews();
        checkCurrentUser();
        setupClickListeners();
    }

    private void initializeFirebase() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    private void initializeViews() {
        emailEdit = findViewById(R.id.Email);
        passwordEdit = findViewById(R.id.Password);
        nameEdit = findViewById(R.id.Name);
        signupBtn = findViewById(R.id.btnSingUp);
        logInBtn = findViewById(R.id.btnLogIn);
    }

    private void checkCurrentUser() {
        if (auth.getCurrentUser() != null) {
            navigateToMain();
            finish();
        }
    }

    private void setupClickListeners() {
        signupBtn.setOnClickListener(v -> attemptRegistration());
        logInBtn.setOnClickListener(v -> navigateToLogin());
    }

    private void attemptRegistration() {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        String name = nameEdit.getText().toString().trim();

        if (!validateInputs(email, password, name)) {
            return;
        }

        registerUser(email, password, name);
    }

    private boolean validateInputs(String email, String password, String name) {
        // Email validation
        if (TextUtils.isEmpty(email)) {
            emailEdit.setError("Email is required");
            emailEdit.requestFocus();
            return false;
        }

        // Password validation
        if (TextUtils.isEmpty(password)) {
            passwordEdit.setError("Password is required");
            passwordEdit.requestFocus();
            return false;
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            passwordEdit.setError("Password must be at least " + MIN_PASSWORD_LENGTH + " characters");
            passwordEdit.requestFocus();
            return false;
        }

        // Name validation
        if (TextUtils.isEmpty(name)) {
            nameEdit.setError("Name is required");
            nameEdit.requestFocus();
            return false;
        }
        return true;
    }

    private void registerUser(@NonNull String email, @NonNull String password,
                              @NonNull String name) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Спочатку зберігаємо дані користувача
                        Log.d("My", "User email: " + auth.getCurrentUser().getEmail());
                        Log.d("My", "User ID: " + auth.getUid());
                        saveUserDataToFirestore(email, name);
                    } else {
                        handleRegistrationError(task.getException());
                    }
                });
    }

    private void saveUserDataToFirestore(String email, String name) {
        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("email", email);

        firestore.collection("users")
                .document(userId)
                .set(userData)
                .addOnSuccessListener(aVoid -> {
                    showToast("Registration Successful");
                    //navigateToMain();
                    //navigateToLogin();
                    navigateToRegData();
                })
                .addOnFailureListener(e -> {
                    showToast("Failed to save user data: " + e.getMessage());
                });
    }

    private void handleRegistrationError(Exception exception) {
        String errorMessage;
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            errorMessage = "Password is too weak";
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            errorMessage = "Invalid email format";
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            errorMessage = "Email already exists";
        } else {
            errorMessage = "Registration failed: " + exception.getMessage();
        }
        showToast(errorMessage);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToMain() {
        startActivity(new Intent(RegActivity.this, MainActivity.class));
        finish();
    }

    private void navigateToLogin() {
        startActivity(new Intent(RegActivity.this, LogInActivity.class));
        finish();
    }

    private void navigateToRegData() {
        startActivity(new Intent(RegActivity.this, RegDataActivity.class));
        finish();
    }
}