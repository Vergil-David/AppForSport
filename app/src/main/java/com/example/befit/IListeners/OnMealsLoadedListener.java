package com.example.befit.IListeners;

import com.example.befit.model.MealItem;

import java.util.List;

public interface OnMealsLoadedListener {
    void onMealsLoaded(List<MealItem> meals);
}
