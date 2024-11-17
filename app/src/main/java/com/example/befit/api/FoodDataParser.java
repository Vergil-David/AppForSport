package com.example.befit.api;

import android.util.Log;

import com.example.befit.model.FoodSearchDesc;
import com.example.befit.model.foodManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FoodDataParser {
    private final String NO_PHOTO_URL = "https://d2eawub7utcl6.cloudfront.net/images/nix-apple-grey.png";
    private List<FoodSearchDesc> parseFoodItems(boolean isBranded, JSONObject jsonObject)  throws Exception {
        String arrayName;
        String jsonFoodNameTag;
        if(isBranded){
            arrayName = "branded";
            jsonFoodNameTag = "brand_name";
        } else {
            arrayName = "common";
            jsonFoodNameTag = "food_name";
        }

        JSONArray array = jsonObject.getJSONArray(arrayName);
        List<FoodSearchDesc> commonFoodItems = new ArrayList<>();
        Log.d("jsonArray", array.toString());
        for (int i = 0; i < array.length(); i++) {
            JSONObject foodObject = array.getJSONObject(i);
            String brandId = isBranded ? foodObject.optString("nix_item_id", "unknown") : "unknown";
            String name = foodObject.getString(jsonFoodNameTag);
            String photoUrl = foodObject.getJSONObject("photo").getString("thumb");
            if(!photoUrl.equals(NO_PHOTO_URL))
                commonFoodItems.add(new FoodSearchDesc(name, photoUrl,brandId));
        }
        return commonFoodItems;
    }
    public List<FoodSearchDesc> parseCommonFoodItems(JSONObject jsonObject) throws Exception {
        return parseFoodItems(false, jsonObject);
    }
    public List<FoodSearchDesc> parseBrandedFoodItems(JSONObject jsonObject) throws Exception {
        return parseFoodItems(true,jsonObject);
    }
    private foodManager parseFoodDetails(boolean isBranded, JSONObject foodObject) throws Exception {
        Log.d("foodObject",foodObject.toString());
        String foodName = isBranded ? foodObject.optString("brand_name") : foodObject.optString("food_name");
        String servingUnit = foodObject.getString("serving_unit");
        String image = foodObject.getJSONObject("photo").getString("thumb");
        int servingQty = foodObject.optInt("serving_qty",0);
        int calories = foodObject.optInt("nf_calories",0);
        int servingGram = foodObject.optInt("serving_weight_grams",0);
        double protein = foodObject.optDouble("nf_protein",0.0);
        double fat = foodObject.optDouble("nf_total_fat",0.0);
        double carbohydrates = foodObject.optDouble("nf_total_carbohydrate",0.0);

        return new foodManager(foodName, image, servingUnit, servingQty
                , calories, servingGram, fat, protein, carbohydrates);
    }

    public foodManager parseBrandedFoodDetails(JSONObject jsonObject)throws Exception {
       return parseFoodDetails(true,jsonObject);
    }
    public foodManager parseCommonFoodDetails(JSONObject jsonObject)throws Exception {
        return parseFoodDetails(false,jsonObject);
    }

}
