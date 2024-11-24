package com.example.befit.DialogFragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.befit.R;

import org.w3c.dom.Text;

public class DayCaloriesInfo extends BaseDialogFragment{
    private static final String ARG_DATE = "date";
    private static final String ARG_CALORIES_BURNED = "caloriesBurned";
    private static final String ARG_CALORIES_GAINED = "caloriesGained";
    private static final String ARG_MAX_BURN_CALORIES = "maxBurnCalories";
    private static final String ARG_MAX_GAIN_CALORIES = "maxGainCalories";

    public static DayCaloriesInfo newInstance(String date, int caloriesBurned, int caloriesGained, int maxBurnCalories, int maxGainCalories) {
        DayCaloriesInfo dialog = new DayCaloriesInfo();
        Bundle args = new Bundle();
        args.putString(ARG_DATE, date);
        args.putInt(ARG_CALORIES_BURNED, caloriesBurned);
        args.putInt(ARG_CALORIES_GAINED, caloriesGained);
        args.putInt(ARG_MAX_BURN_CALORIES, maxBurnCalories);
        args.putInt(ARG_MAX_GAIN_CALORIES, maxGainCalories);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    protected int getLayuoytID() {
        return (R.layout.dialog_custom_day_kcal_info);
    }


    @Override
    protected int getDialogStyle() {
        return R.style.CustomDialogStyle;
    }

    @Override
    protected void setupViews(View dialogView) {
        if (getArguments() != null) {
            String date = getArguments().getString(ARG_DATE);
            int caloriesBurned = getArguments().getInt(ARG_CALORIES_BURNED);
            int caloriesGained = getArguments().getInt(ARG_CALORIES_GAINED);
            int maxGainCalories = getArguments().getInt(ARG_MAX_GAIN_CALORIES);

            int result = maxGainCalories - caloriesGained + caloriesBurned;

            String calcResult = String.format("Calculation: %d - %d + %d = %d",
                    maxGainCalories, caloriesGained, caloriesBurned, result);

            String message;
            if (result > 0) {
                message = String.format("Congratulations! You've burned %d extra calories this day!", result);
            } else if (result < 0) {
                message = String.format("Oops! You've gained %d extra calories this day!", Math.abs(result));
            } else {
                message = "Great balance! You've maintained your calorie target perfectly this day!";
            }

            TextView dateView = dialogView.findViewById(R.id.date);
            TextView calcTextView = dialogView.findViewById(R.id.textCalc);
            TextView infoMessage = dialogView.findViewById(R.id.textInfo);

            dateView.setText(date);
            calcTextView.setText(calcResult);
            infoMessage.setText(message);
        }
    }
}
