package com.example.befit.DataBase;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.befit.model.MealItem;
import com.example.befit.model.SportActivity;
import com.example.befit.model.SportType;
import com.example.befit.tools.FoodListConverter;

@Database(entities = {MealItem.class}, version = 1)
@TypeConverters(FoodListConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MealDao MealDao();
}
