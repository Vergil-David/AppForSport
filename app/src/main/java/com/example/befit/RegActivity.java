package com.example.befit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.util.Log;

import android.content.Intent;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class RegActivity extends Activity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Перевірка, чи користувач авторизований
        if (auth.getCurrentUser() != null) {
            Log.d("My", "Email: " + auth.getCurrentUser().getEmail());
            startActivity(new Intent(RegActivity.this, SettingActivity.class));
            finish();
        } else {
            Log.d("My", "Current user is null");
        }

        EditText Email = (EditText) findViewById(R.id.Email);
        EditText Password = (EditText) findViewById(R.id.Password);
        EditText Name = (EditText) findViewById(R.id.Name);
        EditText Age = (EditText) findViewById(R.id.Year);
        EditText Weight = (EditText) findViewById(R.id.Weight);

        MaterialButton btnToGoToLogIn = (MaterialButton) findViewById(R.id.btnLogIn);

        // Register button
        MaterialButton Signup = (MaterialButton) findViewById(R.id.btnSingUp);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String name = Name.getText().toString();
                String age = Age.getText().toString();
                String weight = Weight.getText().toString();

                // Call signUp method
                signUp(email, password);
            }
        });

        btnToGoToLogIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Наприклад, перехід на екран входу
                    Intent intent = new Intent(RegActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish();
                }
        });

    }

    private void signUp(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Log.d("My", "Email or password is null!");
            return;
        }

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d("My", "Sign Up successful!");
                        startActivity(new Intent(RegActivity.this, SettingActivity.class));
                        finish();
                    } else {
                        Log.d("My", "Sign Up failure!");
                    }
                });
    }
}