package com.example.befit.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.applandeo.materialcalendarview.EventDay;
import com.example.befit.DataBase.FireBaseManager;
import com.example.befit.DialogFragments.DayCaloriesInfo;
import com.example.befit.R;
import com.example.befit.User.CalenderCalories;
import com.example.befit.User.DailyCalories;
import com.example.befit.User.DailyCaloriesManager;
import com.example.befit.User.User;
import com.example.befit.databinding.ActivityCalendarBinding;
import com.example.befit.tools.CaloriesCalculator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity {

    ActivityCalendarBinding binding;

    Map<String, DailyCalories> calender;

    String currentDate;

    int dateBurnedCalories, dateGainedCalories;
    int maxBrnCal, maxGaindCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bayInfo.setVisibility(View.INVISIBLE);
        calender = CalenderCalories.getInstance().getCaloriesMap();
        List<String> datesList = new ArrayList<>(calender.keySet());
        highLightCalendar(datesList);

        binding.calendarView.setOnDayClickListener(eventDay -> {
            Calendar clickedDate = eventDay.getCalendar();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            currentDate = sdf.format(clickedDate.getTime());

            DailyCalories dailyCalories = calender.get(currentDate);
            if (dailyCalories != null) {
                dateBurnedCalories = dailyCalories.getCaloriesBurned();
                dateGainedCalories = dailyCalories.getCaloriesGained();
                maxBrnCal = (int)CaloriesCalculator.calculateCaloriesToBurnForHealth(User.getInstance());
                maxGaindCal = CaloriesCalculator.CalculateCaloriesIntake(User.getInstance());

                binding.bayInfo.setVisibility(View.VISIBLE);

                binding.burnedCalories.setText(String.valueOf(dateBurnedCalories));
                binding.consumedCalories.setText(String.valueOf(dateGainedCalories));
                binding.dailyIntakeCalories.setText(String.valueOf(maxGaindCal));
                binding.dailyCaloriesActivity.setText(String.valueOf(maxBrnCal));
                setProgressBarView(dateGainedCalories, dateBurnedCalories, maxGaindCal, maxBrnCal);
            } else {
                binding.bayInfo.setVisibility(View.INVISIBLE);
            }
        });

        binding.btnTotal.setOnClickListener( v -> {
            DayCaloriesInfo dialog = DayCaloriesInfo.newInstance(
                    currentDate,dateBurnedCalories,dateGainedCalories, maxBrnCal, maxGaindCal
            );
            dialog.show(getSupportFragmentManager(), "DayCaloriesInfo");
        });
    }

    private void setProgressBarView(int dateCalories, int dateBurnedCalories, int maxGained, int maxBurn)
    {
        int progress = getProgress(dateCalories,maxGained);
        binding.foodProgBarOval.setProgress(progress);

        int burnedProgress = getProgress(dateBurnedCalories,maxBurn);
        binding.activityProgBarOval.setProgress(burnedProgress);
    }

    private int getProgress(int currentValue, int maxMalue) {
        int result = 0;
        if (currentValue >= maxMalue)
            result = 100;
        else
            result = (int) ((double) currentValue / maxMalue * 100);
        return result;
    }

    private void highLightCalendar(List<String> datesList){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        List<EventDay> events = new ArrayList<>();
        for (String dateString : datesList) {
            try {
                Date date = sdf.parse(dateString);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                EventDay eventDay = new EventDay(calendar, R.drawable.custom_marker);
                events.add(eventDay);

            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(this, "Помилка парсингу дати", Toast.LENGTH_SHORT).show();
            }
        }
        binding.calendarView.setEvents(events);
    }

}