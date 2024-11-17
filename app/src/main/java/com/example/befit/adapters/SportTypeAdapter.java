package com.example.befit.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.befit.R;
import com.example.befit.activities.SportListActivity;
import com.example.befit.model.SportType;

import java.util.List;

public class SportTypeAdapter extends RecyclerView.Adapter<SportTypeAdapter.ViewHolder> {

    Context context;
    private List<SportType> itemList;

    public SportTypeAdapter(Context context,List<SportType> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport_type_act, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SportType item = itemList.get(position);

        holder.textInfo.setText(item.getName());
        holder.imageView.setImageResource(item.getImageId());

        if (!item.getName().equals(context.getString(R.string.at_home)))
            holder.isComingInfo.setText(context.getString(R.string.coming_soon));
        else
            holder.isComingInfo.setText("");


        holder.itemView.setOnClickListener( v -> {
            Intent intent = new Intent(context, SportListActivity.class);
            intent.putExtra("sportType", item);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textInfo , isComingInfo;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            textInfo = view.findViewById(R.id.textInfo);
            isComingInfo = view.findViewById(R.id.isComingInfo);
        }
    }
}
