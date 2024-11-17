package com.example.befit.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.befit.tools.FoodListConverter;

import org.jetbrains.annotations.PropertyKey;

import java.io.Serializable;
import java.util.List;

@Entity
public class MealItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    @TypeConverters(FoodListConverter.class)
    private List<FoodItem> listFood;

    public MealItem(String name, List<FoodItem> listFood)
    {
        this.name = name;
        this.listFood = listFood;
    }

    public void setId(int id) {this.id = id;}
    public int getId() {return id;}

    public String getName() {return this.name;}
    public List<FoodItem> getListFood() {return this.listFood;}
    public int getTotalCalories()
    {
        int result = 0;
        for(FoodItem item : listFood)
            result += item.getCalories();

        return result;
    }

}
