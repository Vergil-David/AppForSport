package com.example.befit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.befit.model.FoodItem;
import com.example.befit.R;

import java.util.List;

public class FoodNameAdapter extends RecyclerView.Adapter<FoodNameAdapter.FoodViewHolder> {

    private final Context context;
    private final List<FoodItem> foods;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FoodItem foodItem, int position);
    }

    // Конструктор
    public FoodNameAdapter(Context context, List<FoodItem> foods, OnItemClickListener listener) {
        this.context = context;
        this.foods = foods;
        this.listener = listener;
    }

    // Створення нових ViewHolder, коли RecyclerView їх потребує
    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Створюємо новий view з XML-файлу item_food
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    // Прив'язуємо дані до елементів ViewHolder
    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        // Отримуємо об'єкт їжі з поточної позиції
        FoodItem food = foods.get(position);
        // Встановлюємо назву їжі в TextView
        holder.foodName.setText(food.getName());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(food, position));
    }

    // Повертаємо кількість елементів в списку
    @Override
    public int getItemCount() {
        return foods.size();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < foods.size()) {
            foods.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, foods.size());
        }
    }

    // Внутрішній клас ViewHolder, який відповідає за відображення елементів
    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodNameTextView);
        }
    }
}
