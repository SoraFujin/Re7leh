package com.example.hello;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


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
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(restaurant);
            }
        });



    }
    private void showDatePickerDialog(Restaurant restaurant) {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Date");

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.now());
        builder.setCalendarConstraints(constraintsBuilder.build());

        MaterialDatePicker<Long> picker = builder.build();
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selectedDate) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String bookingDate = sdf.format(new Date(selectedDate));

                Intent intent = new Intent(context, RestaurantBooking.class);
                intent.putExtra("restaurantId", restaurant.getId());
                intent.putExtra("bookingDate", bookingDate);
                context.startActivity(intent);
            }
        });

        picker.show(((AppCompatActivity) context).getSupportFragmentManager(), "DatePicker");
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
