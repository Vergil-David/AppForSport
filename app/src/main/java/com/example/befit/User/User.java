package com.example.befit.User;

import com.example.befit.tools.ActivityLevel;
import com.example.befit.tools.Sex;

public class User {
    private static User instance;

    private double weight, height;
    private int age;

    ActivityLevel activityLevel;

    private Sex sex;
    private User() {}

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
}
