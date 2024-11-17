package com.example.befit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.befit.R;

public class MealNameAdapter extends ArrayAdapter<String> {

    public MealNameAdapter(Context context, String[] names) {
        super(context, R.layout.list_meal_name_item, names);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_meal_name_item, parent, false);

        TextView name = view.findViewById(R.id.textMealName);
        name.setText(getItem(position));

        return view;
    }
}
