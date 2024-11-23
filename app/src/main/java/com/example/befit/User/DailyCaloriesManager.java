package com.example.befit.User;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DailyCaloriesManager {
    private static DailyCaloriesManager instance;

    private int caloriesBurned;
    private int caloriesGained;
    private String currentDate;
    private final FirebaseFirestore db;

    private DailyCaloriesManager() {
        currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        caloriesBurned = 0;
        caloriesGained = 0;
        db = FirebaseFirestore.getInstance();
    }

    public static DailyCaloriesManager getInstance() {
        if (instance == null) {
            instance = new DailyCaloriesManager();
        }
        return instance;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public void setCaloriesGained(int caloriesGained) {
        this.caloriesGained = caloriesGained;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public int getCaloriesGained() {
        return caloriesGained;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void addCaloriesBurned(int calories) {
        this.caloriesBurned += calories;
    }

    public void addCaloriesGained(int calories) {
        this.caloriesGained += calories;
    }

    public void resetData() {
        this.caloriesBurned = 0;
        this.caloriesGained = 0;
    }

}
