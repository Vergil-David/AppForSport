package com.example.befit.model;
public class FoodSearchDesc {
    private String name;
    private String imageUrl;
    private String id;

    public FoodSearchDesc(String name, String imageUrl,String id) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getId() {return id;}
}
