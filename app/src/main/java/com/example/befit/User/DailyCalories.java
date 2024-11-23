package com.example.befit.User;

public class DailyCalories {
    private int caloriesBurned;
    private int caloriesGained;

    // Конструктор без аргументів для Firestore
    public DailyCalories() {
        // Ініціалізація полів за замовчуванням
        this.caloriesBurned = 0;
        this.caloriesGained = 0;
    }

    // Конструктор з параметрами
    public DailyCalories(int caloriesBurned, int caloriesGained) {
        this.caloriesBurned = caloriesBurned;
        this.caloriesGained = caloriesGained;
    }

    // Геттери та сеттери для полів
    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public int getCaloriesGained() {
        return caloriesGained;
    }

    public void setCaloriesGained(int caloriesGained) {
        this.caloriesGained = caloriesGained;
    }
}
