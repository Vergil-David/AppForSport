package com.example.befit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.befit.adapters.PostsAdapter;
import com.example.befit.databinding.ActivityPostsBinding;
import com.example.befit.model.PostItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostsActivity extends AppCompatActivity {

    ActivityPostsBinding binding;

    FirebaseDatabase database;
    DatabaseReference myRef;

    List<PostItem> posts = new ArrayList<>();
    PostsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityPostsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance("https://trainingapp-7f481-default-rtdb.europe-west1.firebasedatabase.app/");
        myRef = database.getReference("post");
        loadPostsFromFireBase();


        binding.postsList.setOnItemClickListener(((parent, view, position, id) -> {
            Intent intent = new Intent(this, InfoPostActivity.class);
            PostItem resultPost = posts.get(position);
            intent.putExtra("post" , (Serializable) resultPost);
            startActivity(intent);

        }));
    }

    void updateUI() {
        adapter = new PostsAdapter(this, posts);
        binding.postsList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadPostsFromFireBase() {
        try {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            posts.add(new PostItem(
                                      snapshot.child("Title").getValue().toString()
                                    , snapshot.child("Description").getValue().toString()
                                    , snapshot.child("ImageUrl").getValue().toString()
                                    , snapshot.child("CreatedDate").getValue().toString()
                                )
                            );
                        }
                        updateUI();
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