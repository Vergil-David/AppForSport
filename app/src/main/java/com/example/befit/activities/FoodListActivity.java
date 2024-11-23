package com.example.befit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.befit.DataBase.MealRepository;
import com.example.befit.DialogFragments.MealDetailsDialogFragment;
import com.example.befit.IListeners.OnMealDeletedListener;
import com.example.befit.IListeners.OnUpdateMeals;
import com.example.befit.User.User;
import com.example.befit.model.FoodItem;
import com.example.befit.model.MealItem;
import com.example.befit.IListeners.OnMealSelectedListener;
import com.example.befit.adapters.MealAdapter;
import com.example.befit.DialogFragments.MealDialogFragment;
import com.example.befit.databinding.ActivityFoodListBinding;
import com.example.befit.tools.ActivityLevel;
import com.example.befit.tools.CaloriesCalculator;

import java.util.List;
import java.util.ArrayList;

public class FoodListActivity extends AppCompatActivity
        implements OnMealSelectedListener, OnMealDeletedListener , OnUpdateMeals {

    MealRepository mealRepository;
    ActivityFoodListBinding binding;
    private List<MealItem> meals;
    private MealAdapter mealAdapter;
    private ActivityResultLauncher<Intent> launcher = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityFoodListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mealRepository = new MealRepository(this);
        meals = new ArrayList<>();
        mealAdapter = new MealAdapter(this, meals);
        binding.ListOfFood.setAdapter(mealAdapter);

        launcher  = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    MealItem meal = (MealItem) result.getData().getSerializableExtra("meal");
                    if (meal != null) {
                        meals.add(meal);
                        mealAdapter.notifyDataSetChanged();
                    }
                }
        });

        LoadMealsFromDatabase();
        binding.kcalIntakeText.setText(String.valueOf(CaloriesCalculator.CalculateCaloriesIntake(User.getInstance())) + " kcal");

        binding.frameTest.setOnClickListener( v -> {
            for(MealItem item : meals )
            {
                for(FoodItem foodItem : item.getListFood())
                    Log.d("meals",foodItem.getName());
            }
        });
        binding.btnAdd.setOnClickListener(v -> {
            MealDialogFragment dialogFragment = MealDialogFragment.newInstance();
            dialogFragment.setOnMealSelectedListener(this);
            dialogFragment.show(getSupportFragmentManager(), "MealDialog");
        });
        binding.btnBack.setOnClickListener(v -> {
            startActivity(new Intent(FoodListActivity.this, MainActivity.class));
        });
        binding.ListOfFood.setOnItemClickListener((parent, view, position, id) -> {
            MealDetailsDialogFragment detailsDialog = MealDetailsDialogFragment.newInstance(meals.get(position), position);
            detailsDialog.setOnMealDeletedListener(this);
            detailsDialog.setOnUpdateMealsListener(this);
            detailsDialog.show(getSupportFragmentManager(), "MealDetailsDialog");
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        saveMealsToDatabase();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mealRepository.closeDatabase();
    }
    private void LoadMealsFromDatabase() {
        mealRepository.getAllMeals(loadedMeals -> {
            if(loadedMeals.isEmpty())
                return;
            runOnUiThread(() -> {
                meals.clear();
                meals.addAll(loadedMeals);
                mealAdapter.notifyDataSetChanged();
            });
        });
    }
    private void saveMealsToDatabase() {
        if (meals.isEmpty()) {
            mealRepository.clearAllMeals();
        } else {
            mealRepository.clearAllMeals();
            for (MealItem mealItem : meals) {
                mealRepository.insertMeal(mealItem);
            }
        }
    }
    @Override
    public void onMealSelected(String mealName) {
        Intent intent = new Intent(this, SearchFoodActivity.class);
        intent.putExtra("mealType", mealName);
        launcher.launch(intent);
    }
    @Override
    public void onMealDeleted(int position) {
        meals.remove(position);
        mealAdapter.notifyDataSetChanged();
    }

    @Override
    public void Update() {
        mealAdapter.notifyDataSetChanged();
    }
}
