package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageRestaurants extends AppCompatActivity {

    private RequestQueue queue;
    private List<Restaurants> items = new ArrayList<>();
    private ListView listView;
    private CustomListAdapter<Restaurants> restaurantsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageitem);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ManageRestaurants.this, AddEditHR.class);
                intent.putExtra("type", 1);
                intent.putExtra("op", 0);
                startActivity(intent);
            }
        });

        queue = Volley.newRequestQueue(this);
        listView = findViewById(R.id.listView);
        getRestaurants();
        restaurantsAdapter = new CustomListAdapter<>(this, items);
        listView.setAdapter(restaurantsAdapter);

        // Bottom bar
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

        ImageView reminderIcon = findViewById(R.id.notification_icon);
        reminderIcon.setOnClickListener(e -> {
            Intent intent = new Intent(this, ReminderActivity.class);
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

    private void getRestaurants() {
        String url = "http://10.0.2.2/android/get_restaurants.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                int id = object.getInt("ID");
                                String name = object.getString("Name");
                                String location = object.getString("location");
                                float rating = (float) object.getDouble("rating");
                                String imageName = object.getString("image_name");

                                Restaurants restaurant = new Restaurants(id, name, location, rating, imageName);
                                items.add(restaurant);
                            }
                            restaurantsAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ManageRestaurants.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ManageRestaurants.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        }
        );

        queue.add(request);
    }
}
