package com.example.hello;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello.PopularPlace;

import java.util.List;

public class PopularPlaceAdapter extends RecyclerView.Adapter<PopularPlaceAdapter.ViewHolder> {

    private List<PopularPlace> popularPlaces;

    public PopularPlaceAdapter(List<PopularPlace> popularPlaces) {
        this.popularPlaces = popularPlaces;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_popular_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PopularPlace popularPlace = popularPlaces.get(position);
        holder.placeName.setText(popularPlace.getName());
        holder.placeImage.setImageResource(popularPlace.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return popularPlaces.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView placeName;
        public ImageView placeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.place_name);
            placeImage = itemView.findViewById(R.id.place_image);
        }
    }
}
