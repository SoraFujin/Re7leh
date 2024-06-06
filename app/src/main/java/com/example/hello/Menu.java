package com.example.hello;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hello.PopularPlace;
import com.example.hello.PopularPlaceAdapter;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    private RecyclerView popularPlacesRecyclerView;
    private PopularPlaceAdapter popularPlaceAdapter;
    private List<PopularPlace> popularPlaces;

    // TODO LIST:
    // Dont let the user put the app in horizontal mode
    // Remove exported = true from activity_main

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popularPlacesRecyclerView = findViewById(R.id.popular_places_recycler_view);
        popularPlacesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        popularPlaces = new ArrayList<>();
        //TODO GET THIS DATA FROM PHP BACKEND
        popularPlaces.add(new PopularPlace("Palestine, Jerusalem", R.drawable.jerusalem));
        popularPlaces.add(new PopularPlace("Palestine, Bethlehem", R.drawable.bethlehem));
        popularPlaces.add(new PopularPlace("Palestine, Jerusalem", R.drawable.jerusalem));
        popularPlaces.add(new PopularPlace("Palestine, Bethlehem", R.drawable.bethlehem));
        popularPlaces.add(new PopularPlace("Palestine, Jerusalem", R.drawable.jerusalem));
        popularPlaces.add(new PopularPlace("Palestine, Bethlehem", R.drawable.bethlehem));

        popularPlaceAdapter = new PopularPlaceAdapter(popularPlaces);
        popularPlacesRecyclerView.setAdapter(popularPlaceAdapter);
    }
}
