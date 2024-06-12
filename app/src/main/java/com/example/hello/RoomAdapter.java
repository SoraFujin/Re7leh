package com.example.hello;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
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

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private List<Room> roomList;
    private Context context;

    public RoomAdapter(List<Room> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.room_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.roomType.setText(room.getType());
        holder.roomDescription.setText(room.getDescription());
        holder.roomPrice.setText(String.valueOf(room.getPrice() + "$"));
        holder.bookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(room);

            }
        });
        Glide.with(context).load(room.getImageUrl()).into(holder.roomImage);
    }
    private void showDatePickerDialog(Room room) {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select Date");

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        constraintsBuilder.setValidator(DateValidatorPointForward.now());
        builder.setCalendarConstraints(constraintsBuilder.build());

        MaterialDatePicker<Pair<Long, Long>> picker = builder.build();
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                Long startDateLong = selection.first;
                Long endDateLong = selection.second;

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                String startDate = sdf.format(new Date(startDateLong));
                String endDate = sdf.format(new Date(endDateLong));

                Intent intent = new Intent(context, BookingDetailsActivity.class);
                intent.putExtra("startDate", startDate);
                intent.putExtra("endDate", endDate);
                context.startActivity(intent);
            }
        });

        picker.show(((AppCompatActivity) context).getSupportFragmentManager(), "DateRangePicker");
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView roomType;
        TextView roomDescription;
        TextView roomPrice;

        Button bookNow;
        ImageView roomImage;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomType = itemView.findViewById(R.id.roomTypeTextView);
            roomDescription = itemView.findViewById(R.id.roomDescriptionTextView);
            roomPrice = itemView.findViewById(R.id.roomPriceTextView);
            bookNow = itemView.findViewById(R.id.bookNowButton);
            roomImage = itemView.findViewById(R.id.roomImageView);
        }
    }
}
