package com.example.befit.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.befit.R;
import com.example.befit.adapters.SportActivityAdapter;
import com.example.befit.databinding.ActivitySportListBinding;
import com.example.befit.model.SportType;

public class SportListActivity extends AppCompatActivity {
    ActivitySportListBinding binding;
    SportType sportType;
    SportActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySportListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sportType = (SportType) getIntent().getSerializableExtra("sportType");
        adapter = new SportActivityAdapter(this,sportType.getActivities());

        binding.imageView.setImageResource(sportType.getImageBackGroudId());
        binding.activityList.setAdapter(adapter);
    }
}