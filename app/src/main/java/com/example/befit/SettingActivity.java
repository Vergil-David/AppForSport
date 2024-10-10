package com.example.befit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        ImageButton buttonSetting = findViewById(R.id.btnBack);

        buttonSetting.setOnClickListener(v-> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }
}
