package com.example.befit.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.befit.DataBase.FireBaseManager;
import com.example.befit.R;
import com.example.befit.User.User;
import com.example.befit.databinding.ActivityCalendarBinding;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    ActivityCalendarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        highlightDates(User.getInstance().getId());
    }

    private void highlightDates(String userId) {
        FireBaseManager.getAllDailyCalories(userId, querySnapshot -> {
            List<String> availableDates = new ArrayList<>();

            for (QueryDocumentSnapshot document : querySnapshot) {
                String date = document.getId();
                Log.d("date",date);
                availableDates.add(date);
            }

            // Виклик методу для підсвічення
            highlightDatesOnCalendar(availableDates);

        }, e -> {
            Toast.makeText(this, "Failed to load dates: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    private void highlightDatesOnCalendar(List<String> availableDates) {
        CalendarView calendarView = findViewById(R.id.calendarView);

        // Приклад: додавання повідомлення при виборі дати
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);

            if (availableDates.contains(selectedDate)) {
                // Виведення даних для вибраної дати
                Toast.makeText(this, "Data available for " + selectedDate, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No data for " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });
    }
}