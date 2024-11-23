package com.example.befit.DataBase;

import android.util.Log;

import com.example.befit.User.DailyCalories;
import com.example.befit.model.PostItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FireBaseManager {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Статичний метод для додавання/оновлення даних про калорії
    public static void saveDailyCalories(String userId, String date, int caloriesBurned, int caloriesGained) {
        DailyCalories dailyCalories = new DailyCalories(caloriesBurned, caloriesGained);

        // Збереження або оновлення документа в Firestore
        db.collection("dailyActivivty")
                .document(userId)
                .collection("dailyCalories")
                .document(date)
                .set(dailyCalories)
                .addOnSuccessListener(aVoid -> Log.d("FireBaseManager", "Дані успішно збережено"))
                .addOnFailureListener(e -> Log.e("FireBaseManager", "Помилка при збереженні даних", e));
    }

    // Статичний метод для завантаження даних про калорії
    public static void loadDailyCalories(String userId, String date, OnSuccessListener<DailyCalories> onSuccess, OnFailureListener onFailure) {
        db.collection("dailyActivivty")
                .document(userId)
                .collection("dailyCalories")
                .document(date)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        DailyCalories dailyCalories = documentSnapshot.toObject(DailyCalories.class);
                        if (dailyCalories != null) {
                            onSuccess.onSuccess(dailyCalories);  // передаємо дані до методу-обробника
                        } else {
                            Log.d("FireBaseManager", "Не вдалося перетворити документ в об'єкт DailyCalories");
                            onFailure.onFailure(new Exception("Не вдалося перетворити документ в об'єкт DailyCalories"));
                        }
                    } else {
                        Log.d("FireBaseManager", "Документ за вказаною датою не знайдений");
                        // Якщо документа немає, створюємо новий
                        createNewDailyCalories(userId, date, onSuccess, onFailure);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FireBaseManager", "Помилка при завантаженні даних", e);
                    onFailure.onFailure(e);  // передаємо помилку до методу-обробника
                });
    }

    // Метод для створення нового документа DailyCalories
    private static void createNewDailyCalories(String userId, String date, OnSuccessListener<DailyCalories> onSuccess, OnFailureListener onFailure) {
        // Створення нового об'єкта DailyCalories (можна встановити значення за замовчуванням)
        DailyCalories newDailyCalories = new DailyCalories(0, 0); // наприклад, початкові значення 0

        db.collection("dailyActivivty")
                .document(userId)
                .collection("dailyCalories")
                .document(date)
                .set(newDailyCalories)
                .addOnSuccessListener(aVoid -> {
                    Log.d("FireBaseManager", "Новий документ DailyCalories створено успішно");
                    onSuccess.onSuccess(newDailyCalories);  // передаємо новий об'єкт до методу-обробника
                })
                .addOnFailureListener(e -> {
                    Log.e("FireBaseManager", "Помилка при створенні нового документа DailyCalories", e);
                    onFailure.onFailure(e);  // передаємо помилку до методу-обробника
                });
    }

    public static void getAllDailyCalories(String userId, OnSuccessListener<QuerySnapshot> onSuccess, OnFailureListener onFailure) {
        db.collection("usersCalories")
                .document(userId)
                .collection("dailyCalories")
                .get()
                .addOnSuccessListener(onSuccess)
                .addOnFailureListener(onFailure);
    }


}