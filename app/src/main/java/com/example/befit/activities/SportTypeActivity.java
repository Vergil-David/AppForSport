package com.example.befit.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.befit.R;
import com.example.befit.adapters.SportTypeAdapter;
import com.example.befit.databinding.ActivitySportTypeBinding;
import com.example.befit.model.SportActivity;
import com.example.befit.model.SportType;
import java.util.List;

public class SportTypeActivity extends AppCompatActivity {
    ActivitySportTypeBinding binding;

    SportTypeAdapter adapter;
    List<SportActivity> activities = List.of(
            new SportActivity(R.drawable.squat_jack,"Squat jack"),
            new SportActivity(R.drawable.shoulder_i_y_t, "Shoulder I-Y-T"),
            new SportActivity(R.drawable.triceps_push_up, "Triceps push-up"),
            new SportActivity(R.drawable.triceps_dip, "Triceps dip"),
            new SportActivity(R.drawable.jump_switch_lunge, "Jump switch lunge")
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivitySportTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.horizontalRecyclerView.setLayoutManager(layoutManager);

        List<SportType> sportTypes = List.of(
                new SportType(getString(R.string.at_home),R.drawable.at_home_image, R.drawable.home_backgroud, activities),
                new SportType(getString(R.string.in_the_gym),R.drawable.in_the_gym_image, R.drawable.gym_background, activities),
                new SportType(getString(R.string.outdoor),R.drawable.outdoor_image, R.drawable.outdoor_background, activities),
                new SportType(getString(R.string.yoga),R.drawable.yoga_image, R.drawable.yoga_background, activities)
        );

        adapter = new SportTypeAdapter(this, sportTypes);
        binding.horizontalRecyclerView.setAdapter(adapter);
    }

}