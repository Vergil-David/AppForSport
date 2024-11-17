package com.example.befit.fragments;

import com.example.befit.model.FoodSearchDesc;
import com.example.befit.R;
import com.example.befit.model.foodManager;

import java.util.function.Consumer;

public class CommonFoodListFragment extends BaseFoodListFragment {

    public CommonFoodListFragment() {super(R.layout.fragment_common_food_list);}
    @Override
    public void fetchFoodDetails(FoodSearchDesc foodItem, Consumer<foodManager> onSuccess, Consumer<String> onError) {
        service.fetchCommonFoodDetails(foodItem.getName(), onSuccess, onError);
    }
}
