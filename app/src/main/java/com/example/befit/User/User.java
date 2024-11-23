package com.example.befit.User;

import androidx.lifecycle.MutableLiveData;

import com.example.befit.tools.ActivityLevel;
import com.example.befit.tools.Sex;

public class User {
    private static User instance;

    private String id;
    private String name;
    private double weight, height;
    private int age;

    ActivityLevel activityLevel;

    private Sex sex;

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public void initialize(String name, String id, int age, double weight, double height, Sex sex) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.sex = sex;
        activityLevel = ActivityLevel.LIGHTLY_ACTIVE;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
