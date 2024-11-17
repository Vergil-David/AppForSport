package com.example.befit.activities;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.befit.R;
import com.example.befit.databinding.ActivityInfoPostBinding;
import com.example.befit.model.PostItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoPostActivity extends AppCompatActivity {
    ActivityInfoPostBinding binding;

    PostItem post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityInfoPostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        post = (PostItem) getIntent().getSerializableExtra("post");

        Glide.with(this).load(post.getImageUrl()).into(binding.image);
        binding.title.setText(post.getTitle());
        binding.textDesc.setText(post.getDescription());
        binding.textDesc.setMovementMethod(new ScrollingMovementMethod());
    }


}