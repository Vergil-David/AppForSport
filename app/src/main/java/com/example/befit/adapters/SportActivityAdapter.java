package com.example.befit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.befit.R;
import com.example.befit.model.SportActivity;

import java.util.List;

public class SportActivityAdapter extends ArrayAdapter<SportActivity> {

    public SportActivityAdapter(@NonNull Context context, List<SportActivity> sports) {
        super(context, R.layout.item_sport_activity, sports);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SportActivity item = getItem(position);

       View view = convertView;
       if(view == null)
           view = LayoutInflater.from(getContext()).inflate(R.layout.item_sport_activity, parent, false);

       ImageView image = view.findViewById(R.id.imageView);
       TextView name = view.findViewById(R.id.textName);

       Glide.with(view).asBitmap().load(item.getImageUrl()).into(image);
       name.setText(item.getName());

       return view;
    }
}
