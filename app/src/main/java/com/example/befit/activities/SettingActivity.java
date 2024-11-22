package com.example.befit.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.befit.R;
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

        ConstraintLayout deleteAccountDialog = findViewById(R.id.deleteAccountDialog);
        Button yesButton = findViewById(R.id.yesButton);
        Button noButton = findViewById(R.id.noButton);
        Button deleteAccBtn = findViewById(R.id.btnToDeleteAccount);
        deleteAccBtn.setOnClickListener(v-> {
            deleteAccountDialog.setVisibility(View.VISIBLE);
        });

        // Кнопка "Yes"
        yesButton.setOnClickListener(v -> {


            // Перевіряємо, чи користувач авторизований
            if (auth.getCurrentUser() != null) {
                // Надсилаємо email для підтвердження
                auth.getCurrentUser().sendEmailVerification()
                        .addOnCompleteListener(emailTask -> {
                            if (emailTask.isSuccessful()) {
                                Toast.makeText(this, "Verification email sent. Please check your email to confirm account deletion",
                                        Toast.LENGTH_LONG).show();

                                // Зберігаємо флаг в SharedPreferences, що користувач ініціював видалення
                                SharedPreferences prefs = getSharedPreferences("AccountPrefs", Context.MODE_PRIVATE);
                                prefs.edit().putBoolean("deletionRequested", true).apply();

                                // Показуємо інструкції користувачу
                                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                builder.setTitle("Account Deletion Initiated")
                                        .setMessage("A confirmation email has been sent to your email address. " +
                                                "Please click the verification link in the email to complete account deletion.")
                                        .setPositiveButton("OK", (dialog, which) -> {
                                            // Виходимо з акаунту
                                            FirebaseAuth.getInstance().signOut();
                                            startActivity(new Intent(this, LogInActivity.class));
                                            finish();
                                        })
                                        .show();
                            } else {
                                Toast.makeText(this, "Failed to send verification email",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            deleteAccountDialog.setVisibility(View.GONE);
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

