package com.example.befit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.befit.adapters.VpAdapter;
import com.example.befit.api.FoodService;
import com.example.befit.fragments.BrandedFoodListFragment;
import com.example.befit.fragments.CommonFoodListFragment;
import com.example.befit.model.FoodItem;
import com.example.befit.IListeners.OnFoodAddListener;
import com.example.befit.model.MealItem;
import com.example.befit.adapters.FoodNameAdapter;
import com.example.befit.databinding.ActivitySearchFoodBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class SearchFoodActivity extends AppCompatActivity
        implements OnFoodAddListener {
    ActivitySearchFoodBinding binding;
    List<FoodItem> addedFoodList = new ArrayList<>();
    FoodNameAdapter addedFoodAdapter;
    String mealType;
    private CommonFoodListFragment commonFoodFragment;
    private BrandedFoodListFragment brandedFoodFragment;
    private final FoodService foodService = new FoodService();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivitySearchFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.progressBar.setVisibility(View.INVISIBLE);
        commonFoodFragment = new CommonFoodListFragment();
        brandedFoodFragment = new BrandedFoodListFragment();

        initViewPager();
        setAdapter();

        mealType = getIntent().getStringExtra("mealType");

        binding.foodNameInput.requestFocus();

        binding.btnOk.setOnClickListener(v -> {
            String foodName = binding.foodNameInput.getText().toString().trim();
            binding.progressBar.setVisibility(View.VISIBLE);
            searchFood(foodName);
        });

        binding.btnCreate.setOnClickListener(v -> {
            if (addedFoodList.isEmpty()) {
                Toast.makeText(this, "Please add some food items", Toast.LENGTH_SHORT).show();
                return;
            }

            MealItem meal = new MealItem(mealType, addedFoodList);
            Intent intent = new Intent();
            intent.putExtra("meal", meal);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void initViewPager() {
        List<Fragment> fragments = List.of(commonFoodFragment, brandedFoodFragment);
        List<String> fragNames = List.of("Common", "Branded");

        VpAdapter adapter = new VpAdapter(this, fragments);
        binding.viewPager.setAdapter(adapter);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            tab.setText(fragNames.get(position));
        }).attach();
    }

    private void setAdapter() {
        addedFoodAdapter = new FoodNameAdapter(this, addedFoodList, (foodItem, position) -> {
            addedFoodAdapter.removeItem(position);
        });
        binding.addedFoodList.setAdapter(addedFoodAdapter);
        binding.addedFoodList.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void searchFood(String query) {
        foodService.fetchFoodList(query,
            commonFoods -> {
                runOnUiThread(() -> {
                    commonFoodFragment.updateSearchResults(commonFoods);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                });
            },
            brandedFoods -> {
                runOnUiThread(() -> brandedFoodFragment.updateSearchResults(brandedFoods));
            },
            error -> {
                Log.e("SearchFoodActivity", "Error fetching food: " + error);
                runOnUiThread(() -> Toast.makeText(this, "Request Failed", Toast.LENGTH_SHORT).show());
            });
    }

    @Override
    public void OnFoodAdd(FoodItem food) {
        addedFoodList.add(food);
        if(addedFoodAdapter != null)
            addedFoodAdapter.notifyDataSetChanged();
    }
}