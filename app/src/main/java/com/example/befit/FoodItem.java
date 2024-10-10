package com.example.befit;

public class FoodItem {
    private final String name;
    private final int calories;
    FoodItem(String name,int calories)
    {
        this.name = name;
        this.calories = calories;
    }

    public String getName() {return this.name;}
    public int getCalories() {return this.calories;}

}
