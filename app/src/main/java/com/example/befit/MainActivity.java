package com.example.befit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        FrameLayout buttonStast = findViewById(R.id.activityButton);
        ImageButton buttonFood = findViewById(R.id.btnFood);
        ImageButton buttonSetting = findViewById(R.id.btnSettings);

        buttonStast.setOnClickListener(v -> {
            startActivity(new Intent(this,StatsActivity.class));

        });

        buttonFood.setOnClickListener(v -> {
            startActivity(new Intent(this,FoodListActivity.class));
        });

        buttonSetting.setOnClickListener( v-> {
            startActivity(new Intent(this,SettingActivity.class));
        });

    }
}