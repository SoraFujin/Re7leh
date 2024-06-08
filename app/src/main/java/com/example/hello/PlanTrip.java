package com.example.hello;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class PlanTrip extends AppCompatActivity {
    private String selectedCityName;
    private ImageView landImageView, hotelImageView, resturantImageView, transportationImageView;
    private  FragmentManager fragmentManager = getSupportFragmentManager();
    private ArrayList<LandMark> selectedHotels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_trip);

        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        selectedCityName = prefs.getString("selectedCityName", "");

        landImageView = findViewById(R.id.land_mark_icon);
        hotelImageView = findViewById(R.id.hotel_icon);
        resturantImageView = findViewById(R.id.food_icon);
        transportationImageView = findViewById(R.id.transport_icon);

        selectedHotels = new ArrayList<>();
        selectedHotels.add(new LandMark(R.drawable.abrahams, "Jerusalem Hotel 1"));
        selectedHotels.add(new LandMark(R.drawable.assarayahotel, "Jerusalem Hotel 2"));
        selectedHotels.add(new LandMark(R.drawable.bethlahemhotel, "Bethlehem Hotel 1"));
        selectedHotels.add(new LandMark(R.drawable.grandhotelbethlahem, "Bethlehem Hotel 2"));
        selectedHotels.add(new LandMark(R.drawable.lotushotel, "Bethlehem Hotel 2"));
        selectedHotels.add(new LandMark(R.drawable.saintmichael, "Bethlehem Hotel 2"));

        landImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.landMarkfragmentContainer, LandMarkFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });


        hotelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.landMarkfragmentContainer, HotelFragment.class, null).setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });

        resturantImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.landMarkfragmentContainer, FoodFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });

        transportationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager.beginTransaction()
                        .replace(R.id.landMarkfragmentContainer, TransportFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });

    }
}
