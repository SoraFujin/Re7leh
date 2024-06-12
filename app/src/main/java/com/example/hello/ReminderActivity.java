package com.example.hello;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ReminderActivity extends AppCompatActivity {

    private RecyclerView reminderRecyclerView;
    private ReservationAdapter reservationAdapter;
    private List<Reservation> reservations;
    private RequestQueue requestQueue;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        // Initialize RecyclerView
        reminderRecyclerView = findViewById(R.id.reminder_recycler_view);
        reminderRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        gson = new Gson();
        requestQueue = Volley.newRequestQueue(this);

        fetchReservations();

        // Bottom bar setup
        if (Menu.manager) {
            ImageView managerIcon = findViewById(R.id.manager_icon);
            managerIcon.setVisibility(View.VISIBLE);
            managerIcon.setOnClickListener(e -> {
                Intent intent = new Intent(this, Management.class);
                startActivity(intent);
                finish();
            });
        }

        ImageView homeIcon = findViewById(R.id.home_icon);
        homeIcon.setOnClickListener(e -> {
            Intent intent = new Intent(this, Menu.class);
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

    private void fetchReservations() {
        SharedPreferences pref = getSharedPreferences("MyFavorites", MODE_PRIVATE);
        int userID = pref.getInt("id", 0);
        String url = "http://10.0.2.2/android/getReservations.php?user_id=" + userID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("fetchReservations", "Response: " + response); // Add this line
                        Type listType = new TypeToken<List<Reservation>>() {}.getType();
                        reservations = gson.fromJson(response, listType);
                        Log.d("fetchReservations", "Parsed Reservations: " + reservations); // Add this line
                        reservationAdapter = new ReservationAdapter(reservations);
                        reminderRecyclerView.setAdapter(reservationAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("fetchReservations", "Error: " + error.toString());
            }
        });

        requestQueue.add(stringRequest);
    }

}
