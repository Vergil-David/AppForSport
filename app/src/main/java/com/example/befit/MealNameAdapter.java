package com.example.befit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MealNameAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] names;

    public MealNameAdapter(Context context, String[] names) {
        super(context, R.layout.list_meal_name_item, names);
        this.context = context;
        this.names = names;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = LayoutInflater.from(context).inflate(R.layout.list_meal_name_item, parent, false);

        TextView name = view.findViewById(R.id.textMealName);
        name.setText(names[position]);

        return view;

    }
}
