package com.example.befit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.befit.R;
import com.example.befit.User.User;
import com.example.befit.databinding.ActivitySettingsBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        auth = FirebaseAuth.getInstance();

        Button signOut = findViewById(R.id.btnToSignOut);
        signOut.setOnClickListener(v-> {
            FirebaseAuth.getInstance().signOut(); // Вихід з облікового запису
            navigateToLogin(); // Переходимо на екран входу
        });


        TextView name =  findViewById(R.id.textName);
        name.setText(User.getInstance().getName());

        ConstraintLayout deleteAccountDialog = findViewById(R.id.deleteAccountDialog);
        Button yesButton = findViewById(R.id.yesButton);
        Button noButton = findViewById(R.id.noButton);
        Button deleteAccBtn = findViewById(R.id.btnToDeleteAccount);
        deleteAccBtn.setOnClickListener(v-> {
            deleteAccountDialog.setVisibility(View.VISIBLE);
        });

        // Кнопка "Yes"
        yesButton.setOnClickListener(v -> {



        });

        // Кнопка "No"
        noButton.setOnClickListener(v -> deleteAccountDialog.setVisibility(View.GONE));

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

