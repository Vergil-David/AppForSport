package com.example.befit.tools;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.example.befit.model.FoodItem;

import java.lang.reflect.Type;
import java.util.List;

public class FoodListConverter {
    @TypeConverter
    public static List<FoodItem> fromString(String value) {
        Type listType = new TypeToken<List<FoodItem>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<FoodItem> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
