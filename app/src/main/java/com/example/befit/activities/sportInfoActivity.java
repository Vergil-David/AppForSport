package com.example.befit.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.befit.DataBase.FireBaseManager;
import com.example.befit.R;
import com.example.befit.User.DailyCaloriesManager;
import com.example.befit.User.User;
import com.example.befit.databinding.ActivitySportInfoBinding;
import com.example.befit.model.SportActivity;
import com.example.befit.tools.CaloriesCalculator;

public class sportInfoActivity extends AppCompatActivity {

    ActivitySportInfoBinding binding;

    SportActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivitySportInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activity = (SportActivity) getIntent().getSerializableExtra("sportActivity");

        Glide.with(this).asGif().load(activity.getImageUrl()).into(binding.imageGif);
        binding.name.setText(activity.getName());
        binding.desc.setText(activity.getDesc());
        binding.textDuration.setText(String.valueOf(activity.getDuration()) + "s");
        binding.textSets.setText(String.valueOf(activity.getSets()));

        double result = CaloriesCalculator.CalculateActivity(User.getInstance().getWeight(), activity.getMet(), activity.getDuration() * activity.getSets());
        int truncatedResult = (int) Math.floor(result);
        binding.textMet.setText(String.valueOf(activity.getMet()));

        binding.btnDone.setOnClickListener(v -> {
            DailyCaloriesManager.getInstance().addCaloriesBurned(truncatedResult);
            FireBaseManager.saveDailyCalories(User.getInstance().getId()
                    , DailyCaloriesManager.getInstance().getCurrentDate()
                    ,DailyCaloriesManager.getInstance().getCaloriesBurned()
                    ,DailyCaloriesManager.getInstance().getCaloriesGained());
        });
    }
}