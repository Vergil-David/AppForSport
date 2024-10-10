package com.example.befit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class FoodListActivity extends AppCompatActivity {
    private List<FoodItem> foodItemsList = Arrays.asList(
            new FoodItem("Apple", 95),
            new FoodItem("Banana", 105),
            new FoodItem("Chicken Breast", 165),
            new FoodItem("Broccoli", 55),
            new FoodItem("Rice", 206)
    );
    private String[] mealNames = {"Breakfast", "Dinner", "Supper", "Snack"};
    private List<MealItem> meals;
    private MealAdapter mealAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food_list);

        ImageButton buttonBack = findViewById(R.id.btnBack);
        ImageButton buttonAdd = findViewById(R.id.btnAdd);
        ListView listView = findViewById(R.id.listView);

        meals = new ArrayList<>();
        mealAdapter = new MealAdapter(this, meals);
        listView.setAdapter(mealAdapter);

        buttonAdd.setOnClickListener(v -> {
            try {
                showMealDialog();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        buttonBack.setOnClickListener(v -> {
            startActivity(new Intent(FoodListActivity.this, MainActivity.class));
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(this, meals.get(position).getName(),Toast.LENGTH_SHORT).show();
        });

    }

    private void showMealDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_custom_meal_name, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomDialogStyle);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        ListView mealNameList = dialogView.findViewById(R.id.mealListView);
        MealNameAdapter adapter = new MealNameAdapter(this, mealNames);
        mealNameList.setAdapter(adapter);

        mealNameList.setOnItemClickListener((parent, view, position, id) -> {
            MealItem meal = new MealItem(mealNames[position], foodItemsList);
            meals.add(meal);
            mealAdapter.notifyDataSetChanged();
            dialog.dismiss();
        });
        dialog.show();
    }

}
