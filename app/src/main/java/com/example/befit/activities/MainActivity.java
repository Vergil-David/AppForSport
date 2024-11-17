package com.example.befit.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.befit.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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