package com.example.befit.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.befit.User.DailyCalories;
import com.example.befit.User.DailyCaloriesManager;
import com.example.befit.User.User;
import com.example.befit.databinding.ActivityStatsBinding;
import com.example.befit.tools.CaloriesCalculator;

public class StatsActivity extends AppCompatActivity {
    ActivityStatsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityStatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int currentCalories = DailyCaloriesManager.getInstance().getCaloriesGained();
        int maxCalories = CaloriesCalculator.CalculateCaloriesIntake(User.getInstance());

        binding.dailyIntakeCalories.setText(String.valueOf(maxCalories) + " kcal");
        binding.consumedCalories.setText(String.valueOf(currentCalories));
        int progress = (int) ((double) currentCalories / maxCalories * 100);
        binding.progressBarFood.setProgress(progress);
        binding.foodProgBarOval.setProgress(progress);

        int maxBurnedCalories = (int)CaloriesCalculator.calculateCaloriesToBurnForHealth(User.getInstance());
        int currentBurnedCalories = DailyCaloriesManager.getInstance().getCaloriesBurned();

        binding.dailyCaloriesActivity.setText(String.valueOf(maxBurnedCalories));
        binding.burnedCalories.setText(String.valueOf(currentBurnedCalories));
        int burnedProgress = (int) ((double) currentBurnedCalories / maxBurnedCalories * 100);
        binding.progressBarActivities.setProgress(burnedProgress);
        binding.activityProgBarOval.setProgress(burnedProgress);

        binding.buttonCalendar.setOnClickListener( v-> {
            startActivity(new Intent(this,CalendarActivity.class));
        });
    }
}
