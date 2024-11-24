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

        binding.dailyIntakeCalories.setText(String.valueOf(maxCalories));
        binding.consumedCalories.setText(String.valueOf(currentCalories));

        int progress = getProgress(currentCalories,maxCalories);
        binding.progressBarFood.setProgress(progress);
        binding.foodProgBarOval.setProgress(progress);

        int maxBurnedCalories = (int)CaloriesCalculator.calculateCaloriesToBurnForHealth(User.getInstance());
        int currentBurnedCalories = DailyCaloriesManager.getInstance().getCaloriesBurned();

        binding.dailyCaloriesActivity.setText(String.valueOf(maxBurnedCalories));
        binding.burnedCalories.setText(String.valueOf(currentBurnedCalories));

        int burnedProgress = getProgress(currentBurnedCalories,maxBurnedCalories);
        binding.progressBarActivities.setProgress(burnedProgress);
        binding.activityProgBarOval.setProgress(burnedProgress);

        binding.buttonCalendar.setOnClickListener( v-> {
            startActivity(new Intent(this,CalendarActivity.class));
        });
    }

    private int getProgress(int currentValue, int maxMalue) {
        int result = 0;
        if (currentValue >= maxMalue)
            result = 100;
        else
            result = (int) ((double) currentValue / maxMalue * 100);
        return result;
    }
}
