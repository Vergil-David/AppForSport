package com.example.befit;

import android.util.Log;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.text.InputType;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);

        // Ініціалізація FirebaseAuth
        auth = FirebaseAuth.getInstance();

        ImageButton buttonSetting = findViewById(R.id.btnBack);
        buttonSetting.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));

        Button btnToSignOut = findViewById(R.id.btnToSignOut);
        Button btnToDeleteAcc = findViewById(R.id.btnToDeleteAccount);

        btnToSignOut.setOnClickListener(v -> {
            signOut();
            startActivity(new Intent(SettingActivity.this, LogInActivity.class));
            finish();
        });

        btnToDeleteAcc.setOnClickListener(v -> showDeleteAccountDialog());
    }

    private void signOut() {
        if (auth.getCurrentUser() != null) {
            Log.d("My", "Email: " + auth.getCurrentUser().getEmail());
        } else {
            Log.d("My", "Already signed out!");
            startActivity(new Intent(SettingActivity.this, LogInActivity.class));
            finish();
        }

        auth.signOut();
        Log.d("My", "Sign Out successful!");
        startActivity(new Intent(SettingActivity.this, LogInActivity.class));
        finish();
    }

    private void showDeleteAccountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Видалення акаунту");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText emailInput = new EditText(this);
        emailInput.setHint("Email");
        layout.addView(emailInput);

        final EditText passwordInput = new EditText(this);
        passwordInput.setHint("Пароль");
        passwordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(passwordInput);

        builder.setView(layout);

        builder.setPositiveButton("Видалити", (dialog, which) -> {
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            DeleteAccount(email, password);
        });

        builder.setNegativeButton("Скасувати", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void DeleteAccount(String email, String password) {
        if (auth.getCurrentUser() != null) {
            Log.d("My", "Email: " + auth.getCurrentUser().getEmail());

            AuthCredential credential = EmailAuthProvider.getCredential(email, password);
            auth.getCurrentUser().reauthenticate(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            auth.getCurrentUser().delete()
                                    .addOnCompleteListener(deleteTask -> {
                                        if (deleteTask.isSuccessful()) {
                                            Log.d("My", "Account deleted successfully!");
                                            startActivity(new Intent(SettingActivity.this, LogInActivity.class));
                                            finish();
                                        } else {
                                            Log.d("My", "Account deletion failed: " + deleteTask.getException().getMessage());
                                        }
                                    });
                        } else {
                            Log.d("My", "Reauthentication failed: " + task.getException().getMessage());
                        }
                    });
        } else {
            Log.d("My", "No account!");
            startActivity(new Intent(SettingActivity.this, LogInActivity.class));
            finish();
        }
    }
}