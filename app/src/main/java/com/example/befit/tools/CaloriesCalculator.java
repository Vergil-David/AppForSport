package com.example.befit.tools;

import com.example.befit.User.User;

public class CaloriesCalculator {
    public static double Calculate(double userWeight, double met, int durationSeconds)
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
}
