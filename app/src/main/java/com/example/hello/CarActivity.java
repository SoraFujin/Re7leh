package com.example.hello;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

public class CarActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private CarAdapter carAdapter;

    private List<Car> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        carList = new ArrayList<>();
        carAdapter = new CarAdapter(carList, this);
        recyclerview.setAdapter(carAdapter);
        fetchCars();
    }


    private void fetchCars(){
        String url = "http://10.0.2.2/travelapp/get_cars.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                carList.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String type = jsonObject.getString("type");
                        String description = jsonObject.getString("description");
                        double price = jsonObject.getDouble("price");
                        String imageUrl = jsonObject.getString("image_url");

                        Car car = new Car(type, description, price, imageUrl);
                        carList.add(car);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                carAdapter.notifyDataSetChanged();
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

}
