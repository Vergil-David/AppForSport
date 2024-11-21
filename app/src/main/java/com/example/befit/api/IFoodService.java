package com.example.befit.api;

import com.example.befit.model.FoodSearchDesc;
import com.example.befit.model.foodManager;

import java.util.List;
import java.util.function.Consumer;

public interface IFoodService {
    void fetchFoodList(String query, Consumer<List<FoodSearchDesc>> onCommonSuccess
            , Consumer<List<FoodSearchDesc>> onBrandedSuccess, Consumer<String> onError);

    void fetchCommonFoodDetails(String query, Consumer<foodManager> onSuccess, Consumer<String> onError);

    void fetchBrandedFoodDetails(String nixItemId, Consumer<foodManager> onSuccess, Consumer<String> onError);
}
