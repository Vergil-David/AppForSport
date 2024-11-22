package com.example.befit;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.befit.databinding.ActivityRegDataBinding;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegDataActivity extends AppCompatActivity {
    private ActivityRegDataBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}