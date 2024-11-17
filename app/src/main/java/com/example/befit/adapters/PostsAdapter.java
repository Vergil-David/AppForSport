package com.example.befit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.befit.R;
import com.example.befit.model.PostItem;

import java.util.List;

public class PostsAdapter extends ArrayAdapter<PostItem> {

    public PostsAdapter(@NonNull Context context, List<PostItem> posts) {
        super(context, R.layout.item_post, posts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PostItem post = getItem(position);

        View view = convertView;
        if(view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_post, parent, false);

        ImageView image = view.findViewById(R.id.postImage);
        TextView textTitle = view.findViewById(R.id.textTitle);
        TextView textDate = view.findViewById(R.id.textDate);

        Glide.with(getContext()).load(post.getImageUrl()).into(image);
        textTitle.setText(post.getTitle());
        textDate.setText(post.getPostDate());

        return view;
    }
}
