package com.example.befit.model;

import java.io.Serializable;

public class FoodItem implements Serializable {
    private final String name;
    private final String imageUrl;
    private final String servingUnit;
    private final int calories;
    private final int servingGram;
    private final double totalFat;
    private final double protein;
    private final double carbohydrates;

    public FoodItem(String name, String imageUrl,String servingUnit, int calories, int servingGram
            , double totalFat, double protein,double carbohydrates) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.servingUnit = servingUnit;

        this.calories = calories;
        this.servingGram = servingGram;
        this.totalFat = totalFat;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
    }

    public String getName() {return name;}
    public String getImageUrl() {return imageUrl;}
    public String getServingUnit() {return servingUnit;}

    public int getCalories() {return calories;}
    public int getServingGram() {return servingGram;}
    public double getTotalFat() {return totalFat;}
    public double getProtein() {return protein;}
    public double getCarbohydrates() {return carbohydrates;}

}
