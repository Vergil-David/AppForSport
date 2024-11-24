package com.example.befit.tools;

import com.example.befit.User.User;

public class CaloriesCalculator {
    public static double CalculateActivity(double userWeight, double met, int durationSeconds)
    {
        double durationHours = durationSeconds / 3600.0;
        return userWeight * met * durationHours;
    }

    public static int CalculateCaloriesIntake(User user) {
        double bmr;

        if (user.getSex() == Sex.Male) {
            bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
        } else {
            bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161;
        }

        double activityFactor = user.getActivityLevel().getMultiplier();
        double dailyCalories = bmr * activityFactor;
        return (int) dailyCalories;
    }

    public static double calculateBMR(double weight, double height, int age, boolean isMale) {
        double bmr = 10 * weight + 6.25 * height - 5 * age;
        bmr += isMale ? 5 : -161;
        return bmr;
    }

    public static double calculateTDEE(double bmr, double activityFactor) {
        return bmr * activityFactor;
    }

    public static double calculateCaloriesForExercise(double tdee, double exercisePercentage) {
        return tdee * exercisePercentage;
    }

    public static double calculateCaloriesToBurnForHealth(User user) {
        double bmr = calculateBMR(user.getWeight(), user.getHeight(), user.getAge(), user.getSex() == Sex.Male);

        double tdee = calculateTDEE(bmr, user.getActivityLevel().getMultiplier());

        double caloriesForExercise = calculateCaloriesForExercise(tdee, 0.17);

        return caloriesForExercise;
    }
}
