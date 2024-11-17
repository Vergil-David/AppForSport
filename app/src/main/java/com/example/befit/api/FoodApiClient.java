package com.example.befit.api;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FoodApiClient {
    private static final String API_URL = "https://trackapi.nutritionix.com/v2/search/instant?query=";
    private static final String NUTRIENT_URL = "https://trackapi.nutritionix.com/v2/natural/nutrients";
    private static final String BRANDED_ITEM_URL = "https://trackapi.nutritionix.com/v2/search/item?nix_item_id=";
    private static final String API_ID = "1de37ef2";
    private static final String API_KEY = "21e08596cca0eb0d8cae7d0dccee6f5f";
    private final OkHttpClient client = new OkHttpClient();

    public Request buildSearchRequest(String query) {
        return new Request.Builder()
                .url(API_URL + query)
                .addHeader("x-app-id",API_ID)
                .addHeader("x-app-key",API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
    }

    public Request buildDetailsRequest(String query){
        try {
            JSONObject object = new JSONObject();
            object.put("query", query);
            RequestBody body = RequestBody.create(object.toString()
                    , MediaType.parse("application/json; charset=utf-8"));
            return new Request.Builder()
                    .url(NUTRIENT_URL)
                    .post(body)
                    .addHeader("x-app-id", API_ID)
                    .addHeader("x-app-key", API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Request buildBrandedItemRequest(String nixItemId) {
        return new Request.Builder()
                .url(BRANDED_ITEM_URL + nixItemId)
                .addHeader("x-app-id", API_ID)
                .addHeader("x-app-key", API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();
    }

    public OkHttpClient getClient() {return client;}
}
