package com.example.befit.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
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

        RequestOptions requestOptions = new RequestOptions()
                .transform(new RoundedCorners(50));
       String subName = (item.getName().length()  >= 18 )? item.getName().substring(0,item.getName().length() - 4) + "..." : item.getName();
       name.setText(subName);

        Glide.with(view)
                .asBitmap()
                .load(item.getImageUrl())
                .apply(requestOptions)
                .into(image);

       return view;
    }
}
