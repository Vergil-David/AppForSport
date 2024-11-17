package com.example.befit.DataBase;

import android.content.Context;

import androidx.room.Room;

import com.example.befit.IListeners.OnMealsLoadedListener;
import com.example.befit.model.MealItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MealRepository {
    private final MealDao mealDao;
    private final ExecutorService executorService;

    public MealRepository(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "meal-database").build();
        mealDao = db.MealDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insertMeal(MealItem meal) {
        executorService.execute(() -> mealDao.insertMeal(meal));
    }

    public void getAllMeals(OnMealsLoadedListener listener) {
       executorService.execute(() -> listener.onMealsLoaded(mealDao.getAllMeals()));
    }

    public void clearAllMeals() {
        executorService.execute(mealDao::clearAllMeals);
    }

    public void closeDatabase() {
        executorService.shutdown();
    }
}
