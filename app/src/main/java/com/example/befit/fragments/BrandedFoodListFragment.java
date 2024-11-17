package com.example.befit.fragments;

import com.example.befit.R;
import com.example.befit.model.FoodSearchDesc;
import com.example.befit.model.foodManager;
import java.util.function.Consumer;

public class BrandedFoodListFragment extends BaseFoodListFragment {

    public BrandedFoodListFragment() {super(R.layout.fragment_branded_food_list);}
    @Override
    public void fetchFoodDetails(FoodSearchDesc foodItem, Consumer<foodManager> onSuccess, Consumer<String> onError) {
        service.fetchBrandedFoodDetails(foodItem.getId(), onSuccess, onError);
    }
}