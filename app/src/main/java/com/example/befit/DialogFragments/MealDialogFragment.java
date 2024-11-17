package com.example.befit.DialogFragments;

import android.view.View;
import android.widget.ListView;

import com.example.befit.IListeners.OnMealSelectedListener;
import com.example.befit.R;
import com.example.befit.adapters.MealNameAdapter;

public class MealDialogFragment extends BaseDialogFragment {

    private OnMealSelectedListener listener;
    private static final String[] mealNames = {"Breakfast", "Dinner", "Supper", "Snack"};

    public static MealDialogFragment newInstance() {
        return new MealDialogFragment();
    }
    public void setOnMealSelectedListener(OnMealSelectedListener listener) {
        this.listener = listener;
    }
    @Override
    protected int getLayuoytID() {
        return R.layout.dialog_custom_meal_name;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.CustomDialogStyle;
    }

    @Override
    protected void setupViews(View dialogView) {
        ListView mealNamesView = dialogView.findViewById(R.id.mealListView);
        MealNameAdapter adapter = new MealNameAdapter(getActivity(),mealNames);
        mealNamesView.setAdapter(adapter);

        mealNamesView.setOnItemClickListener((parent, view, position, id) -> {
            if(listener != null)
                listener.onMealSelected(mealNames[position]);
            dismiss();
        });
    }
}

