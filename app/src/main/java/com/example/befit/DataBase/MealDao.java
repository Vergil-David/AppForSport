package com.example.befit.DataBase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.befit.model.MealItem;
import java.util.List;

@Dao
public interface MealDao {
    @Insert
    void insertMeal(MealItem meal);

    @Query("SELECT * FROM mealItem")
    List<MealItem> getAllMeals();

    @Query("DELETE FROM mealItem")
    void clearAllMeals();
}
