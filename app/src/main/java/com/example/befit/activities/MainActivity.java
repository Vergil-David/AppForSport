package com.example.befit.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.befit.DataBase.FireBaseManager;
import com.example.befit.User.DailyCaloriesManager;
import com.example.befit.User.User;
import com.example.befit.databinding.ActivityMainBinding;
import com.example.befit.tools.CaloriesCalculator;
import com.example.befit.tools.Sex;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading user data...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        loadData();

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
    private void loadData()  {
        if (auth.getCurrentUser() == null) {
            // Якщо користувач не увійшов, перенаправити на сторінку реєстрації
            progressDialog.dismiss();
            startActivity(new Intent(this, RegActivity.class));
            finish(); // Закрити поточну активність
            return;
        }
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
                            String name = document.getString("name");
                            String weight = document.getString("weight");
                            String height = document.getString("height");
                            String age = document.getString("age");
                            String gender = document.getString("gender");

                            int ageInt = Integer.parseInt(age);
                            double weightDouble = Double.valueOf(weight);
                            double heightDouble = Double.valueOf(height);
                            Sex sex = gender.equals("Male") ? Sex.Male : Sex.Female;

                            User.getInstance().initialize(name, auth.getUid(), ageInt, weightDouble, heightDouble, sex);

                            if (TextUtils.isEmpty(weight) || TextUtils.isEmpty(height) ||
                                    TextUtils.isEmpty(age) || TextUtils.isEmpty(gender)) {
                                // Якщо поля відсутні, перенаправити на RegDataActivity
                                redirectToRegDataActivity();
                            }
                            else {
                                loadCaloriesData(userId);
                            }
                        } else {
                            // Якщо документа немає, створити базовий документ або перенаправити на RegDataActivity
                            redirectToRegDataActivity();
                        }
                    } else {
                        // Обробка помилок під час отримання документа
                        Toast.makeText(this, "Failed to fetch user data: " + task.getException(), Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void loadCaloriesData(String userId) {
        FireBaseManager.loadDailyCalories(userId, DailyCaloriesManager.getInstance().getCurrentDate(),
                onSucces -> {
                    DailyCaloriesManager.getInstance().setCaloriesGained(onSucces.getCaloriesGained());
                    DailyCaloriesManager.getInstance().setCaloriesBurned(onSucces.getCaloriesBurned());

                    // Тепер все завантажено, оновлюємо прогрес бари
                    progressDialog.dismiss();
                    setProgressBarView();
                },
                onError -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Failed to load daily calories: ", Toast.LENGTH_LONG).show();
                });
    }

    private void redirectToRegDataActivity() {
        progressDialog.dismiss(); // Закриваємо ProgressDialog
        startActivity(new Intent(this, RegDataActivity.class));
        finish();
    }

    private void setProgressBarView()
    {
        int currentCalories = DailyCaloriesManager.getInstance().getCaloriesGained();
        int maxCalories = CaloriesCalculator.CalculateCaloriesIntake(User.getInstance());

        int progress = (int) ((double) currentCalories / maxCalories * 100);
        binding.foodProgBarOval.setProgress(progress);

        int maxBurnedCalories = (int)CaloriesCalculator.calculateCaloriesToBurnForHealth(User.getInstance());
        int currentBurnedCalories = DailyCaloriesManager.getInstance().getCaloriesBurned();

        int burnedProgress = (int) ((double) currentBurnedCalories / maxBurnedCalories * 100);
        binding.activityProgBarOval.setProgress(burnedProgress);
    }
}