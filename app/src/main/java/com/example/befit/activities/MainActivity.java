package com.example.befit.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.befit.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        // перевірка чи зареєстрований
        if (auth.getCurrentUser() == null) {
            // If not logged in, redirect to registration
            startActivity(new Intent(this, RegActivity.class));
            finish(); // Close current activity
            return;
        }

        binding.btnActivity.setOnClickListener(v -> {
            startActivity(new Intent(this, StatsActivity.class));
        });
        binding.btnHelpful.setOnClickListener(v -> {
            startActivity(new Intent(this, PostsActivity.class));
        });
        binding.btnFood.setOnClickListener(v -> {
            startActivity(new Intent(this, FoodListActivity.class));
        });
        binding.btnSport.setOnClickListener(v -> {
            startActivity(new Intent(this, SportTypeActivity.class));
        });
        binding.btnSettings.setOnClickListener( v-> {
            startActivity(new Intent(this, SettingActivity.class));
        });
    }
}