package com.example.hello;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hello.PopularPlace;

import java.util.List;

public class PopularPlaceAdapter extends RecyclerView.Adapter<PopularPlaceAdapter.PopularPlaceViewHolder> {

    private List<PopularPlace> popularPlaces;
    private OnFavoriteClickListener listener;

    public PopularPlaceAdapter(List<PopularPlace> popularPlaces, OnFavoriteClickListener listener) {
        this.popularPlaces = popularPlaces;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PopularPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_popular_place, parent, false);
        return new PopularPlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularPlaceViewHolder holder, int position) {
        PopularPlace popularPlace = popularPlaces.get(position);
        holder.nameTextView.setText(popularPlace.getName());
        Glide.with(holder.imageView.getContext())
                .load("http://10.0.2.2/" + popularPlace.getImage())
                .into(holder.imageView);

        // Set favorite icon based on favorite status
        if (popularPlace.isFavorite()) {
            holder.favoriteIcon.setImageResource(R.drawable.heart_icon);
        } else {
            holder.favoriteIcon.setImageResource(R.drawable.blue_heart_unfilled_icon);
        }

        // Set click listener for favorite icon
        holder.favoriteIcon.setOnClickListener(view -> {
            if (listener != null) {
                listener.onFavoriteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularPlaces.size();
    }

    public static class PopularPlaceViewHolder extends RecyclerView.ViewHolder {
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

    public interface OnFavoriteClickListener {
        void onFavoriteClick(int position);
    }
}
