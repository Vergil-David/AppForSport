package com.example.befit.User;

import android.util.Log;
import android.widget.Toast;

import com.example.befit.DataBase.FireBaseManager;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class CalenderCalories {
   private static CalenderCalories instance;

    private Map<String, DailyCalories> caloriesMap = new HashMap<>();


    public Map<String, DailyCalories> getCaloriesMap() {
        return caloriesMap;
    }

    public static CalenderCalories getInstance() {
        if (instance == null) {
            instance = new CalenderCalories();
        }
        return instance;
    }

    public void LoadCalories(String userId) {
        FireBaseManager.getAllDailyCalories(userId,
                querySnapshot -> {
                    if (querySnapshot.isEmpty()) {
                        Log.d("CalenderCalories", "No data found for user: " + userId);
                        return;
                    }
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        String date = document.getId(); // Дата як ключ

                        // Правильне перетворення типу Long на int
                        long caloriesBurnedLong = document.getLong("caloriesBurned");
                        long caloriesGainedLong = document.getLong("caloriesGained");

                        // Перетворюємо Long в int
                        int caloriesBurned = (int) caloriesBurnedLong;
                        int caloriesGained = (int) caloriesGainedLong;

                        // Збереження даних
                        caloriesMap.put(date, new DailyCalories(caloriesBurned, caloriesGained));
                    }
                    Log.d("CalenderCalories", "Calories data loaded successfully for user: " + userId);

                    // Перевірка, чи дані збереглися
                    Log.d("CalenderCalories", "caloriesMap: " + caloriesMap.toString());
                },
                e -> {
                    Log.e("CalenderCalories", "Error loading calories data: ", e);
                });
    }

}
