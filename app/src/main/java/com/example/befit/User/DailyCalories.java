package com.example.befit.User;

public class DailyCalories {
    private int caloriesBurned;
    private int caloriesGained;
    public DailyCalories() {
        this.caloriesBurned = 0;
        this.caloriesGained = 0;
    }
    public DailyCalories(int caloriesBurned, int caloriesGained) {
        this.caloriesBurned = caloriesBurned;
        this.caloriesGained = caloriesGained;
    }
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
