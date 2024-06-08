package com.example.hello;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class RestaurantActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private RestaurantAdapter restaurantAdapter;
    private List <Restaurant> restaurantList;
    private SearchView searchView;
   private ImageButton backButton;

   @Override
   protected void onCreate (Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_restaurants);
       recyclerview = findViewById(R.id.recyclerview);
       recyclerview.setLayoutManager(new LinearLayoutManager(this));

       restaurantList =  new ArrayList<>();
       restaurantAdapter = new RestaurantAdapter(restaurantList,this);
       recyclerview.setAdapter(restaurantAdapter);

       searchView = findViewById(R.id.searchView);
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
           @Override
           public boolean onQueryTextSubmit(String query) {
               searchRestaurants(query);
               return true;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });

       backButton = findViewById(R.id.backButton);
       backButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               resetSearch();
           }
       });
 fetchRestaurant("");
   }

   public void fetchRestaurant (String location){
       String url = "http://10.0.2.2/travelapp/get_restaurants.php?location=" + location;
       JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
           @Override
           public void onResponse(JSONArray response) {
               restaurantList.clear();
               for (int i = 0; i < response.length(); i++) {
                   try {
                       JSONObject jsonObject = response.getJSONObject(i);
                       int id = jsonObject.getInt("id");
                       String name = jsonObject.getString("name");
                       String location = jsonObject.getString("location");
                       double rating = jsonObject.getDouble("rating");
                       String imageUrl = jsonObject.getString("image_url");

                       Restaurant restaurant = new Restaurant(id, name, location, rating, imageUrl);
                       restaurantList.add(restaurant);
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
               restaurantAdapter.notifyDataSetChanged();


           }
       },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {

                   }
               });
       RequestQueue queue = Volley.newRequestQueue(this);
       queue.add(request);

   }

   public void searchRestaurants (String location){
       fetchRestaurant(location);
       backButton.setVisibility(View.VISIBLE);
   }
   private void resetSearch (){
       searchView.setQuery("",false);
       searchView.clearFocus();
       fetchRestaurant("");
       backButton.setVisibility(View.GONE);
   }
}
