package com.example.befit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.befit.R;
import com.example.befit.User.User;
import com.example.befit.databinding.ActivitySettingsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;

    private static final int MIN_AGE = 5;
    private static final int MAX_AGE = 100;
    private static final int MIN_WEIGHT = 20;
    private static final int MAX_WEIGHT = 590;
    private static final int MIN_HEIGHT = 20;
    private static final int MAX_HEIGHT = 251;

    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeFirebase();
        setupSpinner();
        getDataFromDataStore();
        setupClickListeners();
    }

    private void initializeFirebase() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    private void setupClickListeners(){
        binding.btnToSignOut.setOnClickListener(v -> signOut());
        binding.btnBack.setOnClickListener(v -> goBack());
        binding.btnEdit.setOnClickListener(v -> attemptEdit());
    }

    private void attemptEdit(){
        String name = binding.editName.getText().toString().trim();
        String age = binding.editAge.getText().toString().trim();
        String weight = binding.editWeight.getText().toString().trim();
        String height = binding.editHeight.getText().toString().trim();
        String genderSpinText = binding.genderSpinner.getSelectedItem().toString().trim();

        if (!validateInputs(name, age, weight, height, genderSpinText)) {
            return;
        }

        Edit(name, age, weight, height, genderSpinText);
    }

    private void getDataFromDataStore(){
        binding.editName.setText(User.getInstance().getName());
        binding.editAge.setText(String.valueOf((int) Math.floor(User.getInstance().getAge()))); // конвертую до найближчого нижнього, а після в стрінг
        binding.editWeight.setText(String.valueOf((int) Math.floor(User.getInstance().getWeight())));
        binding.editHeight.setText(String.valueOf((int) Math.floor(User.getInstance().getHeight())));

        // адаптер Spinner
        ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) binding.genderSpinner.getAdapter();

        // Шукаємо індекс значення
        int position = adapter.getPosition(String.valueOf(User.getInstance().getSex()));

        if (position >= 0) {
            binding.genderSpinner.setSelection(position);
        } else {
            Toast.makeText(this, "Gender value not found in options.", Toast.LENGTH_SHORT).show();
        }
    }

    private void Edit(String newName, String newAge, String newWeight, String newHeight, String newGender) {
        String userId = auth.getCurrentUser().getUid();

        // Отримуємо поточні дані користувача
        firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Отримуємо поточні значення
                        String currentName = documentSnapshot.getString("name");
                        String currentAge = documentSnapshot.getString("age");
                        String currentWeight = documentSnapshot.getString("weight");
                        String currentHeight = documentSnapshot.getString("height");
                        String currentGender = documentSnapshot.getString("gender");

                        // Створюємо Map для оновлених даних
                        Map<String, Object> updates = new HashMap<>();

                        // Перевіряємо кожне поле на зміни
                        if (!newName.equals(currentName)) {
                            updates.put("name", newName);
                        }
                        if (!newAge.equals(currentAge)) {
                            updates.put("age", newAge);
                        }
                        if (!newWeight.equals(currentWeight)) {
                            updates.put("weight", newWeight);
                        }
                        if (!newHeight.equals(currentHeight)) {
                            updates.put("height", newHeight);
                        }
                        if (!newGender.equals(currentGender)) {
                            updates.put("gender", newGender);
                        }

                        // Якщо є зміни, оновлюємо дані
                        if (!updates.isEmpty()) {
                            firestore.collection("users")
                                    .document(userId)
                                    .update(updates)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(SettingActivity.this,
                                                "Data updated successfully",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SettingActivity.this, MainActivity.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(SettingActivity.this,
                                                "Failed to update data: " + e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(SettingActivity.this,
                                    "No changes detected",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        navigateToRegData();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SettingActivity.this,
                            "Error getting current data: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

    private void goBack(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void signOut(){
        FirebaseAuth.getInstance().signOut(); // Вихід з облікового запису
        navigateToLogin(); // Переходимо на екран входу
    }

    private void navigateToRegData(){
        startActivity(new Intent(this, RegDataActivity.class));
        finish();
    }

    private void navigateToLogin() {
        startActivity(new Intent(this, LogInActivity.class));
        finish(); // Завершуємо поточну активність, щоб запобігти поверненню назад
    }

    // data validance
    private boolean validateInputs(String name, String age, String weight, String height, String genderSpinText){
        // Name validation
        if (TextUtils.isEmpty(name)) {
            binding.editName.setError("Name is required");
            binding.editName.requestFocus();
            return false;
        }

        // Age validation
        try {
            int ageValue = Integer.parseInt(age);
            if (ageValue < MIN_AGE || ageValue > MAX_AGE) {
                binding.editAge.setError("Age must be between " + MIN_AGE + " and " + MAX_AGE);
                binding.editAge.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            binding.editAge.setError("Invalid age");
            binding.editAge.requestFocus();
            return false;
        }

        // Weight validation
        try {
            int weightValue = Integer.parseInt(weight);
            if (weightValue < MIN_WEIGHT || weightValue > MAX_WEIGHT) {
                binding.editWeight.setError("Weight must be between " + MIN_WEIGHT + " and " + MAX_WEIGHT);
                binding.editWeight.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            binding.editWeight.setError("Invalid weight");
            binding.editWeight.requestFocus();
            return false;
        }

        // Height validation
        try {
            int heightValue = Integer.parseInt(height);
            if (heightValue < MIN_HEIGHT || heightValue > MAX_HEIGHT) {
                binding.editHeight.setError("Height must be between " + MIN_HEIGHT + " and " + MAX_HEIGHT);
                binding.editHeight.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            binding.editHeight.setError("Invalid height");
            binding.editHeight.requestFocus();
            return false;
        }

        // Gender validation
        if (genderSpinText.equals("Change your gender")) {
            Toast.makeText(binding.getRoot().getContext(), "Please select a valid gender.", Toast.LENGTH_SHORT).show();
            binding.genderSpinner.requestFocus();
            return false;
        }

        return true;
    }

    // spinner setup
    private void setupSpinner() {
        // Створюємо кастомний адаптер, який наслідується від ArrayAdapter
        class CustomSpinnerAdapter extends ArrayAdapter<CharSequence> {
            public CustomSpinnerAdapter(Context context, int resource, CharSequence[] objects) {
                super(context, resource, objects);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setTextColor(getResources().getColor(R.color.white));
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                textView.setTextColor(getResources().getColor(R.color.white));
                return view;
            }
        }

        // Створюємо адаптер з використанням нашого кастомного класу
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(
                this,
                android.R.layout.simple_spinner_item,
                getResources().getTextArray(R.array.gender_options_settings)
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.genderSpinner.setAdapter(adapter);

        // Можна залишити OnItemSelectedListener для додаткової надійності
        binding.genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null) {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}