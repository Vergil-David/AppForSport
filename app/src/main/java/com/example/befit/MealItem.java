package com.example.befit;
import java.util.List;
public class MealItem {
    String name;
    List<FoodItem> listFood;

    MealItem(String name, List<FoodItem> listFood)
    {
        this.name = name;
        this.listFood = listFood;
    }
    String getName() {return this.name;}
    List<FoodItem> getListFood() {return this.listFood;}

    int getTotalCalories()
    {
        int result = 0;
        for(FoodItem item : listFood)
            result += item.getCalories();

        return result;
    }

}
