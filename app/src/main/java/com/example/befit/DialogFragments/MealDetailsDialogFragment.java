package com.example.befit.DialogFragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.befit.DataBase.FireBaseManager;
import com.example.befit.IListeners.OnFoodDeleteListener;
import com.example.befit.IListeners.OnUpdateMeals;
import com.example.befit.User.DailyCaloriesManager;
import com.example.befit.User.User;
import com.example.befit.model.FoodItem;
import com.example.befit.IListeners.OnMealDeletedListener;
import com.example.befit.model.MealItem;
import com.example.befit.R;
import com.example.befit.adapters.FoodDescAdapter;

import java.util.List;

public class MealDetailsDialogFragment extends BaseDialogFragment  implements OnFoodDeleteListener {

    private static final String ARG_MEAL = "meal";
    private static final String ARG_POSITION = "position";
    private MealItem mealItem;
    private int position;
    private OnMealDeletedListener listener;
    private OnUpdateMeals onUpdateMealsListener;
    private ListView foodList;

    public void setOnMealDeletedListener(OnMealDeletedListener listener) {
        this.listener = listener;
    }
    public void setOnUpdateMealsListener(OnUpdateMeals listener)
    {
        this.onUpdateMealsListener = listener;
    }
    public static MealDetailsDialogFragment newInstance(MealItem meal, int position) {
        MealDetailsDialogFragment dialog = new MealDetailsDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MEAL, meal);
        args.putInt(ARG_POSITION, position);
        dialog.setArguments(args);
        return dialog;
    }
    @Override
    protected int getLayuoytID() {
        return R.layout.dialog_custom_meal_details;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.CustomDialogStyle1;
    }

    @Override
    protected void setupViews(View dialogView) {
        if (getArguments() != null) {
            mealItem = (MealItem) getArguments().getSerializable(ARG_MEAL);
            position = getArguments().getInt(ARG_POSITION);
        }

        TextView disc = dialogView.findViewById(R.id.dialogTitle);
        foodList = dialogView.findViewById(R.id.foodDetailsList);
        TextView kcal = dialogView.findViewById(R.id.TextTotalKcal);
        Button deleteMeal = dialogView.findViewById(R.id.btnDelete);
        Button eatMeal = dialogView.findViewById(R.id.btnEat);

        if (mealItem != null) {
            disc.setText(mealItem.getName());
            kcal.setText("Total Calories: " + mealItem.getTotalCalories());
            List<FoodItem> list = mealItem.getListFood();
            FoodDescAdapter adapter = new FoodDescAdapter(requireContext(), list);
            adapter.setListener(this);
            foodList.setAdapter(adapter);
        }

        deleteMeal.setOnClickListener(v -> {
           new AlertDialog.Builder(requireContext())
                   .setTitle("Delete meal")
                   .setMessage("Are you sure?")
                   .setPositiveButton("Yes", (dialogInterfase, witch) -> {
                       if (listener != null) {
                           listener.onMealDeleted(position);
                       }
                       dismiss();
                   })
                   .setNegativeButton("No",(dalogIntefase, witch) -> {
                       dalogIntefase.dismiss();
                   })
                   .show();
        });

        eatMeal.setOnClickListener(v -> {
            DailyCaloriesManager.getInstance().addCaloriesGained(mealItem.getTotalCalories());
            FireBaseManager.saveDailyCalories(User.getInstance().getId()
                    , DailyCaloriesManager.getInstance().getCurrentDate()
                    ,DailyCaloriesManager.getInstance().getCaloriesBurned()
                    ,DailyCaloriesManager.getInstance().getCaloriesGained());

            if (listener != null) {
                listener.onMealDeleted(position);
            }
            dismiss();
        });
    }

    @Override
    public void OnFoodDelete(int position) {
        try {
            mealItem.getListFood().remove(position);
            ((FoodDescAdapter) foodList.getAdapter()).notifyDataSetChanged();
            if(onUpdateMealsListener != null)
                onUpdateMealsListener.Update();
        } catch (Exception ex) {
            Log.d("exception", ex.getMessage());
        }
    }

}
