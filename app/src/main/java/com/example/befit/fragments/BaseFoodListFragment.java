package com.example.befit.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.befit.DialogFragments.AddFoodDescDialogFragment;
import com.example.befit.IListeners.OnFoodAddListener;
import com.example.befit.R;
import com.example.befit.adapters.SearchFoodAdapter;
import com.example.befit.api.FoodService;
import com.example.befit.model.FoodSearchDesc;
import com.example.befit.model.foodManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class BaseFoodListFragment extends Fragment {
    protected SearchFoodAdapter adapter;
    protected List<FoodSearchDesc> foodList = new ArrayList<>();
    protected FoodService service = new FoodService();

    public BaseFoodListFragment(int layoutId) {super(layoutId);}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView foodListView = view.findViewById(R.id.searchFoodList);
        adapter = new SearchFoodAdapter(getActivity(), foodList);
        foodListView.setAdapter(adapter);

        foodListView.setOnItemClickListener((parent, view1, position, id) -> {
            fetchFoodDetails(foodList.get(position), this::showFoodDetailsDialog,
                    error -> requireActivity().runOnUiThread(() ->
                            Toast.makeText(getActivity(),"Failed to fetch food details", Toast.LENGTH_SHORT).show()));
        });
    }
    public void updateSearchResults(List<FoodSearchDesc> newFoodList) {
        foodList.clear();
        foodList.addAll(newFoodList);
        if (adapter != null)
            adapter.notifyDataSetChanged();
    }
    public void showFoodDetailsDialog(foodManager foodManager) {
        AddFoodDescDialogFragment dialog = new AddFoodDescDialogFragment();
        dialog.setFoodManager(foodManager);
        dialog.setOnFoodAddListener((OnFoodAddListener) getActivity());
        dialog.show(getChildFragmentManager(), "AddFoodDescDialogFragment");
    }
    public abstract void fetchFoodDetails(FoodSearchDesc foodItem, Consumer<foodManager> onSuccess, Consumer<String> onError);
}