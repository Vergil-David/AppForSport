package com.example.befit;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.befit.activities.MainActivity;
import com.example.befit.activities.RegActivity;
import com.example.befit.databinding.ActivityRegDataBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegDataActivity extends AppCompatActivity {
    private ActivityRegDataBinding binding;
    private static final int MIN_AGE = 5;
    private static final int MAX_AGE = 100;
    private static final int MIN_WEIGHT = 20;
    private static final int MAX_WEIGHT = 590;
    private static final int MIN_HEIGHT = 20;
    private static final int MAX_HEIGHT = 251;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeFirebase();
        checkCurrentUser();
        setupClickListeners();
    }

    private void attemptStore() {
        String weight = binding.weightText.getText().toString().trim();
        String height = binding.heightText.getText().toString().trim();
        String age = binding.ageText.getText().toString().trim();
        String genderSpinText = binding.genderSpinner.getSelectedItem().toString().trim();

        if (!validateInputs(weight, height, age, genderSpinText)) {
            return;
        }

        storeData(weight, height, age, genderSpinText);
    }

    private boolean validateInputs(String weight, String height, String age, String genderSpinText) {
        // Age validation
        try {
            int ageValue = Integer.parseInt(age);
            if (ageValue < MIN_AGE || ageValue > MAX_AGE) {
                binding.ageText.setError("Age must be between " + MIN_AGE + " and " + MAX_AGE);
                binding.ageText.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            binding.ageText.setError("Invalid age");
            binding.ageText.requestFocus();
            return false;
        }

        // Weight validation
        try {
            int weightValue = Integer.parseInt(weight);
            if (weightValue < MIN_WEIGHT || weightValue > MAX_WEIGHT) {
                binding.weightText.setError("Weight must be between " + MIN_WEIGHT + " and " + MAX_WEIGHT);
                binding.weightText.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            binding.weightText.setError("Invalid weight");
            binding.weightText.requestFocus();
            return false;
        }

        // Weight validation
        try {
            int heightValue = Integer.parseInt(height);
            if (heightValue < MIN_HEIGHT || heightValue > MAX_HEIGHT) {
                binding.heightText.setError("Height must be between " + MIN_HEIGHT + " and " + MAX_HEIGHT);
                binding.heightText.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            binding.heightText.setError("Invalid weight");
            binding.heightText.requestFocus();
            return false;
        }

        // Gender validation
        if (genderSpinText.equals("Select your gender")) {
            Toast.makeText(binding.getRoot().getContext(), "Please select a valid gender.", Toast.LENGTH_SHORT).show();
            binding.genderSpinner.requestFocus();
            return false;
        }

        return true;
    }


    private void storeData(@NonNull String weight, @NonNull String height,
                           @NonNull String age, @NonNull String gender) {
        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        Map<String, Object> userData = new HashMap<>();
        userData.put("weight", weight);
        userData.put("height", height);
        userData.put("age", age);
        userData.put("gender", gender);

        firestore.collection("users")
                .document(userId)
                .update(userData) // Використовуємо update замість set
                .addOnSuccessListener(aVoid -> {
                    showToast("Data updated successfully");
                    navigateToMain();
                })
                .addOnFailureListener(e -> {
                    if (e instanceof FirebaseFirestoreException &&
                            ((FirebaseFirestoreException) e).getCode() == FirebaseFirestoreException.Code.NOT_FOUND) {
                        // Якщо документа не існує, створити новий
                        firestore.collection("users")
                                .document(userId)
                                .set(userData)
                                .addOnSuccessListener(aVoid -> {
                                    showToast("Data added successfully");
                                    navigateToMain();
                                })
                                .addOnFailureListener(ex -> showToast("Failed to create document: " + ex.getMessage()));
                    } else {
                        showToast("Failed to update data: " + e.getMessage());
                    }
                });
    }

    private void initializeFirebase() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    private void checkCurrentUser() {
        if (auth.getCurrentUser() == null) {
            navigateToReg();
            finish();
        }
    }

    private void setupClickListeners() {
        binding.btnEnter.setOnClickListener(v -> attemptStore());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToReg() {
        startActivity(new Intent(RegDataActivity.this, RegActivity.class));
        finish();
    }

    private void navigateToMain() {
        startActivity(new Intent(RegDataActivity.this, MainActivity.class));
        finish();
    }
}