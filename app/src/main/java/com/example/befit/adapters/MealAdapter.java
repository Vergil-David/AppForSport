package com.example.befit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.befit.model.FoodItem;
import com.example.befit.model.MealItem;
import com.example.befit.R;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;
public class MealAdapter extends ArrayAdapter<MealItem> {
    public MealAdapter(Context context, List<MealItem> meals) {
        super(context, 0, meals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MealItem meal = getItem(position);

        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_meal, parent, false);

        TextView mealNameTextView = view.findViewById(R.id.mealNameTextView);
        TextView caloriesTextView = view.findViewById(R.id.caloriesTextView);
        FlexboxLayout foodList = view.findViewById(R.id.ListOfFood);

        if (meal != null) {
            mealNameTextView.setText(meal.getName());
            caloriesTextView.setText("Total Calories: " + meal.getTotalCalories());

            foodList.removeAllViews();

            for (FoodItem foodItem : meal.getListFood()) {
                View foodItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_food, foodList, false);

                TextView foodNameTextView = foodItemView.findViewById(R.id.foodNameTextView);
                foodNameTextView.setText(". " + foodItem.getName());

                foodList.addView(foodItemView);
            }
        }
        return view;
    }

}


