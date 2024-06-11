package com.example.hello;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WishlistActivity extends AppCompatActivity {

    private RecyclerView wishlistRecyclerView;
    private PopularPlaceAdapter wishlistAdapter;
    private List<PopularPlace> wishlistPlaces;

    private static final String PREFS_NAME = "MyFavorites";
    private static final String FAVORITES_KEY = "FavoritePlaces";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        if (Menu.manager) {
            ImageView managerIcon = findViewById(R.id.manager_icon);
            managerIcon.setVisibility(View.VISIBLE);
            managerIcon.setOnClickListener(e -> {
                Intent intent = new Intent(this, Management.class);
                startActivity(intent);
                finish();
            });
        }

        // Initialize RecyclerView
        wishlistRecyclerView = findViewById(R.id.wishlist_recycler_view);
        wishlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load favorite places from SharedPreferences
        loadFavorites();

        // Bottom bar
        ImageView homeIcon = findViewById(R.id.home_icon);
        homeIcon.setOnClickListener(e -> {
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
            finish();
        });
    }

    // Method to load favorite places from SharedPreferences
    private void loadFavorites() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String jsonFavorites = prefs.getString(FAVORITES_KEY, null);
        Log.d("FAVORITES", "loadFavorites: " + jsonFavorites);
        if (jsonFavorites != null) {
            Type type = new TypeToken<ArrayList<PopularPlace>>() {}.getType();
            wishlistPlaces = new Gson().fromJson(jsonFavorites, type);
        } else {
            wishlistPlaces = new ArrayList<>(); // Initialize empty list if SharedPreferences is empty
        }

        // Initialize and set up adapter
        wishlistAdapter = new PopularPlaceAdapter(wishlistPlaces, new PopularPlaceAdapter.OnFavoriteClickListener() {
            @Override
            public void onFavoriteClick(int position) {
                removeFavorite(position);
            }
        });
        wishlistRecyclerView.setAdapter(wishlistAdapter);
    }

    // Method to remove favorite from the list and SharedPreferences
    private void removeFavorite(int position) { try {
        PopularPlace place = wishlistPlaces.get(position);
        place.setFavorite(false); // Update the favorite status
        wishlistPlaces.remove(position); // Remove from the list

        // Update SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String jsonFavorites = new Gson().toJson(wishlistPlaces);
        editor.putString(FAVORITES_KEY, jsonFavorites);
        editor.apply();

        // Notify adapter about item removal
        wishlistAdapter.notifyItemRemoved(position);
        Toast.makeText(this, place.getName() + " Removed from wishlist.", Toast.LENGTH_SHORT).show();
    } catch (Exception e) {
        PopularPlace place = wishlistPlaces.get(0);
        place.setFavorite(false); // Update the favorite status
        wishlistPlaces.remove(0); // Remove from the list

        // Update SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String jsonFavorites = new Gson().toJson(wishlistPlaces);
        editor.putString(FAVORITES_KEY, jsonFavorites);
        editor.apply();

        // Notify adapter about item removal
        wishlistAdapter.notifyItemRemoved(position);
        Toast.makeText(this, place.getName() + " Removed from wishlist.", Toast.LENGTH_SHORT).show();
    }

    }
}
