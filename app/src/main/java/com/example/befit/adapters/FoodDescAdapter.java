package com.example.befit.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.befit.model.FoodItem;
import com.example.befit.R;

import java.util.List;
import java.util.Objects;

public class FoodDescAdapter extends ArrayAdapter {

    public FoodDescAdapter(Context context, List<FoodItem> food) {
        super(context, R.layout.list_meal_name_item, food);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        FoodItem foodItem = (FoodItem) getItem(position);

        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_food_desc, parent, false);


        TextView name = view.findViewById(R.id.textFoodName);
        ImageView image = view.findViewById(R.id.foodPhoto);
        TextView kcal = view.findViewById(R.id.foodCalories);
        TextView ServingGram = view.findViewById(R.id.foodServingGram);
        TextView fat = view.findViewById(R.id.foodFat);
        TextView protein = view.findViewById(R.id.foodProtein);
        TextView carbohydrates = view.findViewById(R.id.foodCarbohydrates);


        name.setText(Objects.requireNonNull(foodItem).getName());
        kcal.setText("Calories: " + foodItem.getCalories());
        Glide.with(getContext()).load(foodItem.getImageUrl()).into(image);
        ServingGram.setText("Serving size: " + (foodItem.getServingGram()));
        fat.setText("Total Fat: " + (foodItem.getTotalFat()));
        protein.setText("Protein: " + (foodItem.getProtein()));
        carbohydrates.setText("Carbohydrates: " + (foodItem.getCarbohydrates()));

        return view;
    }
}
