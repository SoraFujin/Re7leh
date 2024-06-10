package com.example.hello;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class PlanTrip extends AppCompatActivity {
    private String selectedCityName;
    private ImageView landImageView, hotelImageView, resturantImageView, transportationImageView;
    private  FragmentManager fragmentManager = getSupportFragmentManager();
    private ArrayList<LandMark> selectedHotels;
    private Button submit;

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
        submit = findViewById(R.id.submitbtn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);

                String cityName = prefs.getString("selectedCityName", "");
                String selectedLandmark = prefs.getString("selectedPlaceName", "");
                String selectedHotel = prefs.getString("selectedHotelName", "");
                String selectedRestaurant = prefs.getString("selectedRestaurantName", "");
                String selectedTransport = prefs.getString("selectedTransport", "");

                if (cityName.isEmpty() || selectedLandmark.isEmpty() || selectedHotel.isEmpty()
                        || selectedRestaurant.isEmpty() || selectedTransport.isEmpty()) {

                    Toast.makeText(PlanTrip.this, "Please select all options before submitting", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(PlanTrip.this, Menu.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

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
