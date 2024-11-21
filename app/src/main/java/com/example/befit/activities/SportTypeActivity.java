package com.example.befit.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.befit.R;
import com.example.befit.adapters.PostsAdapter;
import com.example.befit.adapters.SportTypeAdapter;
import com.example.befit.databinding.ActivitySportTypeBinding;
import com.example.befit.model.PostItem;
import com.example.befit.model.SportActivity;
import com.example.befit.model.SportType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;

public class SportTypeActivity extends AppCompatActivity {
    ActivitySportTypeBinding binding;

    FirebaseDatabase database;
    DatabaseReference myRef;
    SportTypeAdapter adapter;
    List<SportActivity> activities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivitySportTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance("https://trainingapp-7f481-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("activities");
        loadActivitiesFromFireBase();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.horizontalRecyclerView.setLayoutManager(layoutManager);

        List<SportType> sportTypes = List.of(
                new SportType(getString(R.string.at_home),R.drawable.at_home_image, R.drawable.home_backgroud, activities),
                new SportType(getString(R.string.in_the_gym),R.drawable.in_the_gym_image, R.drawable.gym_background, activities),
                new SportType(getString(R.string.outdoor),R.drawable.outdoor_image, R.drawable.outdoor_background, activities),
                new SportType(getString(R.string.yoga),R.drawable.yoga_image, R.drawable.yoga_background, activities)
        );

        adapter = new SportTypeAdapter(this, sportTypes);
        binding.horizontalRecyclerView.setAdapter(adapter);
    }
    private void loadActivitiesFromFireBase() {
        try {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        try {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                // Отримуємо значення з Firebase
                                String imageUrl = snapshot.child("ImageUrl").getValue().toString();
                                String name = snapshot.child("Name").getValue().toString();
                                String description = snapshot.child("Description").getValue().toString();
                                // Перевіряємо та безпечно отримуємо числові значення
                                long durationLong = snapshot.child("Duration").getValue(Long.class);  // отримуємо як Long
                                long repsLong = snapshot.child("Reps").getValue(Long.class);  // отримуємо як Long
                                long setsLong = snapshot.child("Sets").getValue(Long.class);  // отримуємо як Long

                                // Переводимо Long в int
                                int duration = (int) durationLong;
                                int reps = (int) repsLong;
                                int sets = (int) setsLong;
                                // Створюємо нову активність
                                SportActivity sportActivity = new SportActivity(imageUrl, name, description, duration, reps, sets);

                                // Додаємо активність до списку
                                activities.add(sportActivity);
                            }
                        }catch (Exception ex) {
                            Log.d("exept", ex.getMessage());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("onCancelled", "Failed to read value.", error.toException());
                }
            });
        } catch (Exception e)
        {
            Log.d("dbExeption", e.toString());
        }
    }

}