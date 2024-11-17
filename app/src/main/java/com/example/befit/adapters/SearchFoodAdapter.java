package com.example.befit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.befit.model.FoodSearchDesc;
import com.example.befit.R;

import java.util.List;

public class SearchFoodAdapter extends ArrayAdapter<FoodSearchDesc> {

    public SearchFoodAdapter(Context context, List<FoodSearchDesc> foodItems) {
        super(context, R.layout.item_search_food, foodItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FoodSearchDesc foodItem = getItem(position);

        View view = convertView;
        if(view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_search_food, parent, false);

        TextView foodNameTextView = view.findViewById(R.id.foodName);
        ImageView foodImageView = view.findViewById(R.id.foodPhoto);

        foodNameTextView.setText(foodItem.getName());
        Glide.with(getContext())
                .load(foodItem.getImageUrl())
                .into(foodImageView);

        return view;
    }

}
