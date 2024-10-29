package com.example.befit;

import android.util.Log;
import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import android.content.Intent;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends Activity{

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Використання правильного файлу розмітки
        setContentView(R.layout.activity_login); // Змінити на activity_login

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Перевірка, чи користувач авторизований
        if (auth.getCurrentUser() != null) {
            Log.d("My", "Email: " + auth.getCurrentUser().getEmail());
        } else {
            Log.d("My", "Current user is null");
        }

        if (auth.getCurrentUser() != null) {  // Перевірка на null
            Log.d("My", "Already signed out!");
            startActivity(new Intent(LogInActivity.this, SettingActivity.class));
            return;
        }
        EditText Email = (EditText) findViewById(R.id.Email);
        EditText Password = (EditText) findViewById(R.id.Password);

        // to log in
        MaterialButton btnToGoToSignUp = (MaterialButton) findViewById(R.id.btnToCreate);

        MaterialButton btnSignUp = (MaterialButton) findViewById(R.id.btnSingUp);

        // Handle Sign Up button click
        btnSignUp.setOnClickListener(v -> {
            // Тут додайте логіку для входу, наприклад:
            String email = Email.getText().toString();
            String password = Password.getText().toString();
            signIn(email, password);
        });

        // Handle Go to Sign Up button click
        btnToGoToSignUp.setOnClickListener(v -> {
            // Перехід до активності реєстрації
            startActivity(new Intent(LogInActivity.this, RegActivity.class));
        });
    }

    // Method for signing in the user
    private void signIn(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Log.d("My", "Email or password is null!");
            return;
        }
        Log.d("My", "wtf is that!");
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("My", "Sign In successful!");
                        startActivity(new Intent(LogInActivity.this, SettingActivity.class));
                        finish();
                    } else {
                        Log.d("My", "Sign In failure! " + task.getException().getMessage());
                    }
                    Log.d("My", email + " " + password);
                });
    }
}
