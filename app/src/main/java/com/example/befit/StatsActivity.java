package com.example.befit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_stats);

        ImageButton buttonBack = findViewById(R.id.btnBack);

        buttonBack.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}
