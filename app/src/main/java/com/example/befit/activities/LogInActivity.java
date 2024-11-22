package com.example.befit.activities;

import android.text.TextUtils;
import android.util.Log;
import android.os.Bundle;
import android.widget.EditText;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.befit.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LogInActivity extends AppCompatActivity {
    private static final String TAG = "LogInActivity";

    private FirebaseAuth auth;
    private EditText emailEdit;
    private EditText passwordEdit;
    private MaterialButton signInButton;
    private MaterialButton goToSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeFirebase();
        initializeViews();
        checkCurrentUser();
        setupClickListeners();
    }

    private void initializeFirebase() {
        auth = FirebaseAuth.getInstance();
    }

    private void initializeViews() {
        emailEdit = findViewById(R.id.Email);
        passwordEdit = findViewById(R.id.Password);
        signInButton = findViewById(R.id.btnSingUp);
        goToSignUpButton = findViewById(R.id.btnToCreate);
    }

    private void checkCurrentUser() {
        if (auth.getCurrentUser() != null) {
            Log.d(TAG, "User already logged in: " + auth.getCurrentUser().getEmail());
            navigateToMain();
            finish();
        }
    }

    private void setupClickListeners() {
        signInButton.setOnClickListener(v -> attemptSignIn());
        goToSignUpButton.setOnClickListener(v -> navigateToRegistration());
    }

    private void attemptSignIn() {
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        if (!validateInput(email, password)) {
            return;
        }

        signIn(email, password);
    }

    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            emailEdit.setError("Email is required");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEdit.setError("Password is required");
            return false;
        }

        return true;
    }

    private void signIn(@NonNull String email, @NonNull String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Перевірка, чи підтверджена електронна адреса
                        if (auth.getCurrentUser() != null) {
                            Log.d(TAG, "Sign in successful and email verified");
                            navigateToMain();
                            finish();
                        } else {
                            Log.w(TAG, "Email not verified");
                            Toast.makeText(this, "Please verify your email address before signing in", Toast.LENGTH_LONG).show();
                            auth.signOut(); // Вийти з акаунта, якщо не підтверджено
                        }
                    } else {
                        handleSignInError(task.getException());
                    }
                });
    }

    private void handleSignInError(Exception exception) {
        String errorMessage;
        if (exception instanceof FirebaseAuthInvalidUserException) {
            errorMessage = "No account found with this email";
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            errorMessage = "Invalid email or password";
        } else {
            errorMessage = "Sign in failed: " + exception.getMessage();
        }
        Log.e(TAG, "Sign in error: " + errorMessage, exception);
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    private void navigateToMain() {
        startActivity(new Intent(LogInActivity.this, MainActivity.class));
    }

    private void navigateToRegistration() {
        startActivity(new Intent(LogInActivity.this, RegActivity.class));
    }
}