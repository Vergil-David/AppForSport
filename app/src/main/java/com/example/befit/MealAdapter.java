package com.example.befit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
public class MealAdapter extends ArrayAdapter<MealItem> {
    private final Context context;
    private final List<MealItem> meals;
    public MealAdapter(Context context, List<MealItem> meals) {
        super(context, 0, meals);
        this.context = context;
        this.meals = meals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MealItem meal = getItem(position);

        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false);

        TextView mealNameTextView = view.findViewById(R.id.mealNameTextView);
        TextView caloriesTextView = view.findViewById(R.id.caloriesTextView);
        LinearLayout foodList = view.findViewById(R.id.ListOfFood);

        if (meal != null) {
            mealNameTextView.setText(meal.getName());
            caloriesTextView.setText("Total Calories: " + meal.getTotalCalories());

            foodList.removeAllViews();

            for (FoodItem foodItem : meal.getListFood()) {
                View foodItemView = LayoutInflater.from(context).inflate(R.layout.item_food, foodList, false);

                TextView foodNameTextView = foodItemView.findViewById(R.id.foodNameTextView);
                foodNameTextView.setText(". " + foodItem.getName());

                foodList.addView(foodItemView);
            }
        }
        return view;
    }

}


