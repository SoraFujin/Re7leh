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
import androidx.core.util.Pair;
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
        holder.carImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(car);
            }
        });
    }

    private void showDatePickerDialog(Car car) {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select Rental Dates");

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.now());
        builder.setCalendarConstraints(constraintsBuilder.build());

        MaterialDatePicker<Pair<Long, Long>> picker = builder.build();
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                Long startDateLong = selection.first;
                Long endDateLong = selection.second;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String startDate = sdf.format(new Date(startDateLong));
                String endDate = sdf.format(new Date(endDateLong));

                Intent intent = new Intent(context, CarBooking.class);
                intent.putExtra("carId", car.getId());
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
                context.startActivity(intent);
            }
        });

        picker.show(((AppCompatActivity) context).getSupportFragmentManager(), "DateRangePicker");
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