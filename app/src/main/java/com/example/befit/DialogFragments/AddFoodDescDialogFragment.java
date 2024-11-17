package com.example.befit.DialogFragments;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.befit.IListeners.OnFoodAddListener;
import com.example.befit.R;
import com.example.befit.model.foodManager;

public class AddFoodDescDialogFragment extends BaseDialogFragment {
    OnFoodAddListener listener;
    private foodManager foodManager;

    public void setOnFoodAddListener(OnFoodAddListener listener) {this.listener = listener;}

    public void setFoodManager(foodManager manager) {this.foodManager = manager;}
    @Override
    protected int getLayuoytID() {return R.layout.dialog_food_add_desc;}

    @Override
    protected int getDialogStyle() {return R.style.CustomDialogStyle2;}

    @Override
    protected void setupViews(View dialogView) {
        if (foodManager == null) {
            Log.e("AddFoodDescDialogFragment", "foodManager is null");
            return;
        }

        ImageView foodImage = dialogView.findViewById(R.id.foodPhoto);
        TextView foodName = dialogView.findViewById(R.id.foodName);
        TextView servingQty = dialogView.findViewById(R.id.textCount);
        TextView foodServUnit = dialogView.findViewById(R.id.textDesc);
        TextView foodCalories = dialogView.findViewById(R.id.foodCalories);
        TextView foodGram = dialogView.findViewById(R.id.foodServingGram);
        TextView foodFat = dialogView.findViewById(R.id.foodFat);
        TextView foodProtein = dialogView.findViewById(R.id.foodProtein);
        TextView foodCarbohydrates = dialogView.findViewById(R.id.foodCarbohydrates);

        Button increaseButton = dialogView.findViewById(R.id.btnIncrease);
        Button reduseButton = dialogView.findViewById(R.id.btnReduse);
        Button closeButton = dialogView.findViewById(R.id.closeButton);
        Button addButton = dialogView.findViewById(R.id.addButton);

        foodName.setText(foodManager.getName());
        servingQty.setText(String.valueOf(foodManager.getServingQty()));
        foodServUnit.setText(foodManager.getServingUnit());
        foodCalories.setText(String.valueOf(foodManager.getCalories()));
        foodGram.setText(String.valueOf(foodManager.getServingGram()) + "g");
        foodFat.setText("Total Fat: " + foodManager.getTotalFat() + "g");
        foodProtein.setText("Protein: " + foodManager.getProtein() + "g");
        foodCarbohydrates.setText("Carbohydrates: " + foodManager.getCarbohydrates() + "g");

        Glide.with(this)
                .load(foodManager.getImageUrl())
                .into(foodImage);

        increaseButton.setOnClickListener(v -> {
            foodManager.increaseQty();
            updateDialogValues(dialogView);
        });
        reduseButton.setOnClickListener(v -> {
            foodManager.reduceQty();
            updateDialogValues(dialogView);
        });
        closeButton.setOnClickListener(v -> {
            dismiss();
        });
        addButton.setOnClickListener( v-> {
            if(listener != null) {
                listener.OnFoodAdd(foodManager.getFoodItem());
                dismiss();
            }
        });

    }
    private void updateDialogValues(View dialogView) {
        TextView servingQty = dialogView.findViewById(R.id.textCount);
        TextView foodCalories = dialogView.findViewById(R.id.foodCalories);
        TextView foodGram = dialogView.findViewById(R.id.foodServingGram);
        TextView foodFat = dialogView.findViewById(R.id.foodFat);
        TextView foodProtein = dialogView.findViewById(R.id.foodProtein);
        TextView foodCarbohydrates = dialogView.findViewById(R.id.foodCarbohydrates);

        servingQty.setText(String.valueOf(foodManager.getServingQty()));
        foodCalories.setText(String.valueOf(foodManager.getCalories()));
        foodGram.setText(foodManager.getServingGram() + "g");
        foodFat.setText("Total Fat: " + foodManager.getTotalFat() + "g");
        foodProtein.setText("Protein: " + foodManager.getProtein() + "g");
        foodCarbohydrates.setText("Carbohydrates: " + foodManager.getCarbohydrates() + "g");
    }


}
