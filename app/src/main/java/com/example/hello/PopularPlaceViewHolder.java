package com.example.hello;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PopularPlaceViewHolder extends RecyclerView.ViewHolder {
    public TextView nameTextView;
    public ImageView imageView;
    public ImageView favoriteIcon;

    public PopularPlaceViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.place_name);
        imageView = itemView.findViewById(R.id.place_image);
        favoriteIcon = itemView.findViewById(R.id.favorite_icon);
    }
}
