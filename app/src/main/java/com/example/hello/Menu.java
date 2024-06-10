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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    private static final String PREFS_NAME = "MyFavorites";
    private static final String FAVORITES_KEY = "FavoritePlaces";

    private RecyclerView popularPlacesRecyclerView;
    private PopularPlaceAdapter popularPlaceAdapter;
    private List<PopularPlace> popularPlaces;
    private RequestQueue requestQueue;
    private Gson gson;
    private SharedPreferences sharedPreferences;
    private ImageView tour;

    static public boolean manager = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tour = findViewById(R.id.tour_icon);

        tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, PlanTrip.class);
                startActivity(intent);
                finish();
            }
        });

        setContentView(R.layout.activity_main);

        // Check if the user is a manager
        Intent externalIntent = getIntent();
        String value = externalIntent.getStringExtra("permission");

        if (value != null) {
            if (value.equals("manager")) {
                manager = true;
            }
        }

        if (manager) {
            ImageView managerIcon = findViewById(R.id.manager_icon);
            managerIcon.setVisibility(View.VISIBLE);
            managerIcon.setOnClickListener(e -> {
                Intent intent = new Intent(this, Management.class);
                startActivity(intent);
                finish();
            });
        }


        popularPlacesRecyclerView = findViewById(R.id.popular_places_recycler_view);
        popularPlacesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        popularPlaces = new ArrayList<>();
        popularPlaceAdapter = new PopularPlaceAdapter(popularPlaces, new PopularPlaceAdapter.OnFavoriteClickListener() {
            @Override
            public void onFavoriteClick(int position) {
                toggleFavorite(position);
            }
        });
        popularPlacesRecyclerView.setAdapter(popularPlaceAdapter);

        requestQueue = Volley.newRequestQueue(this);
        gson = new Gson();
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        loadItems();

        ImageView hotelIcon = findViewById(R.id.hotel_icon);
        hotelIcon.setOnClickListener(e -> {
            Intent intent = new Intent(this, MainHotelActivity.class);
            startActivity(intent);
            finish();
        });

        ImageView favouriteIcon = findViewById(R.id.favorites_icon);
        favouriteIcon.setOnClickListener(e -> {
            Intent intent = new Intent(this, WishlistActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadItems() {
        String baseURL = "http://10.0.2.2/android/getPlaces.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String name = object.getString("name");
                                String image = object.getString("imageURL");
                                Log.d("image", image);
                                PopularPlace tripPlaces = new PopularPlace(name, image);
                                tripPlaces.setFavorite(isFavorite(name));
                                popularPlaces.add(tripPlaces);
                            }
                            // Notify adapter that dataset has changed
                            popularPlaceAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.e("loadItems", "Error parsing JSON: " + e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Menu.this, "Error fetching data: " + error.toString(), Toast.LENGTH_LONG).show();
                Log.e("loadItems", "Volley Error: " + error.toString());
            }
        });

        requestQueue.add(stringRequest);
    }

    // Toggle favorite status and update SharedPreferences
    private void toggleFavorite(int position) {
        PopularPlace place = popularPlaces.get(position);
        place.setFavorite(!place.isFavorite());
        saveFavorites();
        popularPlaceAdapter.notifyItemChanged(position);
        if (place.isFavorite())
            Toast.makeText(this, place.getName() + " Added to wishlist.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, place.getName() + " Removed from wishlist.", Toast.LENGTH_SHORT).show();
    }

    // Save the list of favorite places to SharedPreferences as a JSON string
    private void saveFavorites() {
        List<PopularPlace> favoritePlaces = new ArrayList<>();
        for (PopularPlace place : popularPlaces) {
            if (place.isFavorite()) {
                favoritePlaces.add(place);
            }
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String jsonFavorites = gson.toJson(favoritePlaces);
        editor.putString(FAVORITES_KEY, jsonFavorites);
        editor.apply();
    }

    // Check if the place is marked as favorite in SharedPreferences
    private boolean isFavorite(String placeName) {
        String jsonFavorites = sharedPreferences.getString(FAVORITES_KEY, null);
        if (jsonFavorites != null) {
            Type type = new TypeToken<ArrayList<PopularPlace>>() {}.getType();
            List<PopularPlace> favoritePlaces = gson.fromJson(jsonFavorites, type);
            for (PopularPlace place : favoritePlaces) {
                if (place.getName().equals(placeName)) {
                    return true;
                }
            }
        }
        return false;
    }

    }


