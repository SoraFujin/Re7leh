package com.example.hello;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlanTrip extends AppCompatActivity {
    private String selectedCityName;
    private ImageView landImageView, hotelImageView, resturantImageView, transportationImageView;
    private  FragmentManager fragmentManager = getSupportFragmentManager();
    private ArrayList<LandMark> selectedHotels;
    private Button submit;

    //TODO onStop method to delete the old data if the user exits

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

                SharedPreferences pref2 = getSharedPreferences("MyFavorites", MODE_PRIVATE);
                int userID = pref2.getInt("id", 0);

                String cityName = prefs.getString("selectedCityName", "");
                int cityID = prefs.getInt("cityID", 0);

                String selectedLandmark = prefs.getString("selectedPlaceName", "");
                int landmarkID = prefs.getInt("placeID", 0);

                String selectedHotel = prefs.getString("selectedHotelName", "");
                int hotelID = prefs.getInt("hotelID", 0);


                String selectedRestaurant = prefs.getString("selectedRestaurantName", "");
                int restaurantID = prefs.getInt("restaurantID", 0);

                String selectedTransport = prefs.getString("selectedTransport", "");


                if (cityName.isEmpty() || selectedLandmark.isEmpty() || selectedHotel.isEmpty()
                        || selectedRestaurant.isEmpty() || selectedTransport.isEmpty()) {

                    Toast.makeText(PlanTrip.this, "Please select all options before submitting", Toast.LENGTH_SHORT).show();
                }else {
                    reservationNew(cityName, cityID,selectedLandmark, landmarkID, selectedHotel, hotelID, selectedRestaurant, restaurantID,
                            selectedTransport, userID);

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
    private void reservationNew(String cityName, int cityID, String placeName, int placeID, String hotelName, int hotelID
            ,String restaurantName, int restaurantID, String carName, int userID){
        String url = "http://10.0.2.2/android/add_reservation.php";

        RequestQueue queue = Volley.newRequestQueue(this);


        // Create a JsonObjectRequest with POST method
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("city_name", cityName);
                params.put("city_id", String.valueOf(cityID));
                params.put("place_name", placeName);
                params.put("place_id", String.valueOf(placeID));
                params.put("hotel_name", hotelName);
                params.put("hotel_id", String.valueOf(hotelID));
                params.put("resturant_name", restaurantName);
                params.put("restaurant_id", String.valueOf(restaurantID));
                params.put("car_name",carName);
                params.put("userID", String.valueOf(userID));

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }
}
