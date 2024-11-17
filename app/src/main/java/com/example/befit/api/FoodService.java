package com.example.befit.api;

import android.util.Log;
import androidx.annotation.NonNull;
import com.example.befit.model.FoodSearchDesc;
import com.example.befit.model.foodManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FoodService {
    private FoodApiClient client;
    private FoodDataParser parser;
    public FoodService() {
        this.client = new FoodApiClient();
        this.parser = new FoodDataParser();
    }
    public void fetchFoodList(String query, Consumer<List<FoodSearchDesc>> onCommonSuccess
            ,Consumer<List<FoodSearchDesc>> onBrandedSuccess, Consumer<String> onError) {

        client.getClient().newCall(client.buildSearchRequest(query)).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                onError.accept("Failed to fetch food list");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        List<FoodSearchDesc> commonFoods = parser.parseCommonFoodItems(object);
                        List<FoodSearchDesc> brandedFoods = parser.parseBrandedFoodItems(object);

                        onCommonSuccess.accept(commonFoods);
                        onBrandedSuccess.accept(brandedFoods);

                    } catch (Exception e) {
                        onError.accept("Failed to parse food list");
                    }
                } else {
                    Log.d("Request", "Request failed: " + response.message());
                    onError.accept("Request failed: " + response.message());
                }
            }
        });
    }
    public void fetchCommonFoodDetails(String query, Consumer<foodManager> onSuccess, Consumer<String> onError){

        client.getClient().newCall(client.buildDetailsRequest(query)).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                onError.accept("Failed to fetch food details");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        JSONArray array = object.getJSONArray("foods");

                        Log.d("array",array.toString());
                        JSONObject foodObject = array.getJSONObject(0);
                        foodManager foodManager = parser.parseCommonFoodDetails(foodObject);
                        onSuccess.accept(foodManager);
                    } catch (Exception e) {
                        e.printStackTrace();
                        onError.accept("Failed to parse food details");
                    }
                } else {
                    onError.accept("Request failed: " + response.message());
                }
            }
        });
    }

    public void fetchBrandedFoodDetails(String nixItemId, Consumer<foodManager> onSuccess, Consumer<String> onError) {
        client.getClient().newCall(client.buildBrandedItemRequest(nixItemId)).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                onError.accept("Failed to fetch food details");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        JSONArray foodsArray = object.getJSONArray("foods");
                        JSONObject foodObject = foodsArray.getJSONObject(0);
                        foodManager foodManager = parser.parseBrandedFoodDetails(foodObject);
                        onSuccess.accept(foodManager);

                    } catch (Exception e) {
                        e.printStackTrace();
                        onError.accept("Failed to parse food details");
                    }
                } else {
                    onError.accept("Request failed: " + response.message());
                }
            }
        });
    }
}
