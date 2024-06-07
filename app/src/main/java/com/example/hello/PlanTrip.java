package com.example.hello;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class PlanTrip extends AppCompatActivity {
    private ImageView landImageView, hotelImageView, resturantImageView, transportationImageView;
    private  FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_trip);

        landImageView = findViewById(R.id.land_mark_icon);
        hotelImageView = findViewById(R.id.hotel_icon);
        resturantImageView = findViewById(R.id.food_icon);
        transportationImageView = findViewById(R.id.transport_icon);

        landImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.landMarkfragmentContainer, HotelFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });


        hotelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.landMarkfragmentContainer, TransportFragment.class, null).setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });

        resturantImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.landMarkfragmentContainer, LandMarkFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });

        transportationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.landMarkfragmentContainer, FoodFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });

    }




}
