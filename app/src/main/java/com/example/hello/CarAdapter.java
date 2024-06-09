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

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private List<Car> carList;
    private Context context;

    public CarAdapter(List<Car> carList, Context context) {
        this.carList = carList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.car_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = carList.get(position);
        holder.typeTextView.setText(car.getType());
        holder.descriptionTextView.setText(car.getDescription());
        holder.price.setText(String.valueOf(car.getPrice()));
        String formattedPrice =  String.format("%.2f", car.getPrice()) + "$";
        holder.price.setText(formattedPrice);
        Glide.with(context).load(car.getImageUrl()).into(holder.carImageView);
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView typeTextView;
        TextView descriptionTextView;

        TextView price;
        ImageView carImageView;



        ViewHolder(@NonNull View itemView){
        super(itemView);
        typeTextView = itemView.findViewById(R.id.TypeTextView);
        descriptionTextView = itemView.findViewById(R.id.carDiscreptionTextView);
        price = itemView.findViewById(R.id.carPrice);
        carImageView = itemView.findViewById(R.id.carImageView);
    }
}
}