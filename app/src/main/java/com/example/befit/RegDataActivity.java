package com.example.befit;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.befit.databinding.ActivityRegDataBinding;

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