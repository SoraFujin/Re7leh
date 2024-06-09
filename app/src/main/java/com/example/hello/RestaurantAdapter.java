package com.example.hello;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private List<Restaurant> restaurantList;
    private Context context;

    public RestaurantAdapter(List<Restaurant> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.restaurant_item , parent , false);
    return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.nameTextView.setText(restaurant.getName());
        holder.locationTextView.setText(restaurant.getLocation());
        holder.ratingsTextView.setText(String.valueOf(restaurant.getRating()));
        Glide.with(context).load(restaurant.getImageUrl()).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTextView;
        TextView locationTextView;
        TextView ratingsTextView;
        ImageView imageView;
        public ViewHolder (@NonNull View itemView){
            super(itemView);
            nameTextView = itemView.findViewById(R.id.restaurantNameTextView);
            locationTextView = itemView.findViewById(R.id.restaurantLocationTextView);
            ratingsTextView = itemView.findViewById(R.id.restaurantrating);
            imageView = itemView.findViewById(R.id.restaurantImageView);
        }
    }


}
