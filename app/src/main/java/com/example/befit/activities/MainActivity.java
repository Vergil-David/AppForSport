package com.example.befit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.befit.RegDataActivity;
import com.example.befit.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.firebase.firestore.DocumentSnapshot;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        // перевірка чи зареєстрований
        if (auth.getCurrentUser() == null) {
            // Якщо користувач не увійшов, перенаправити на сторінку реєстрації
            startActivity(new Intent(this, RegActivity.class));
            finish(); // Закрити поточну активність
            return;
        } else {
            // Отримати ID користувача
            String userId = auth.getCurrentUser().getUid();

            // Перевірка полів у Firestore
            firestore.collection("users")
                    .document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                // Перевірка, чи поля заповнені
                                String weight = document.getString("weight");
                                String height = document.getString("height");
                                String age = document.getString("age");
                                String gender = document.getString("gender");

                                if (TextUtils.isEmpty(weight) || TextUtils.isEmpty(height) ||
                                        TextUtils.isEmpty(age) || TextUtils.isEmpty(gender)) {
                                    // Якщо поля відсутні, перенаправити на RegDataActivity
                                    startActivity(new Intent(this, RegDataActivity.class));
                                    finish();
                                }
                            } else {
                                // Якщо документа немає, створити базовий документ або перенаправити на RegDataActivity
                                startActivity(new Intent(this, RegDataActivity.class));
                                finish();
                            }
                        } else {
                            // Обробка помилок під час отримання документа
                            Toast.makeText(this, "Failed to fetch user data: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    });
        }

        Log.d("My", "User email: " + auth.getCurrentUser().getEmail());
        Log.d("My", "User ID: " + auth.getUid());

        binding.btnActivity.setOnClickListener(v -> {
            startActivity(new Intent(this, StatsActivity.class));
        });
        binding.btnHelpful.setOnClickListener(v -> {
            startActivity(new Intent(this, PostsActivity.class));
        });
        binding.btnFood.setOnClickListener(v -> {
            startActivity(new Intent(this, FoodListActivity.class));
        });
        binding.btnSport.setOnClickListener(v -> {
            startActivity(new Intent(this, SportTypeActivity.class));
        });
        binding.btnSettings.setOnClickListener( v-> {
            startActivity(new Intent(this, SettingActivity.class));
        });
    }
}