package com.example.befit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.befit.R;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        Button signOut = findViewById(R.id.btnToSignOut);
        signOut.setOnClickListener(v-> {
            FirebaseAuth.getInstance().signOut(); // Вихід з облікового запису
            navigateToLogin(); // Переходимо на екран входу
        });

        ImageButton buttonSetting = findViewById(R.id.btnBack);

        buttonSetting.setOnClickListener(v-> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish(); // Завершуємо поточну активність, щоб запобігти поверненню назад
    }
}

