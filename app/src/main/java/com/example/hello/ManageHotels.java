package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageHotels extends AppCompatActivity {

    private RequestQueue queue;
    private List<Hotels> items = new ArrayList<>();
    private ListView listView;
    private CustomListAdapter<Hotels> hotelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manageitem);
        setSpinner();

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( ManageHotels.this, AddEditHR.class);
                intent.putExtra("type", 0);
                intent.putExtra("op", 0);
                startActivity(intent);
            }
        });

        queue = Volley.newRequestQueue(this);
        listView = findViewById(R.id.listView);
        getHotels();
        hotelAdapter = new CustomListAdapter<>(this, items);
        listView.setAdapter(hotelAdapter);
    }

    private void setSpinner() {
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options_array, R.layout.spinner_item_layout);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Redirect to selected activity from dropdown menu
                String selectedOption = parent.getItemAtPosition(position).toString();
                switch (selectedOption) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void getHotels() {
        String url = "http://10.0.2.2/android/get_hotels.php";

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

                                Hotels hotel = new Hotels(id, name, location, rating, imageName);
                                items.add(hotel);
                            }
                            hotelAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ManageHotels.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ManageHotels.this, "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        }
        );

        queue.add(request);
    }

}
