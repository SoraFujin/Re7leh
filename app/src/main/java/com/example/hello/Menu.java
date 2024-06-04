package com.example.hello;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    private List<TripPlaces> items = new ArrayList<>();
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.main_menu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(layoutManager);
        setSpinner();
        loadItems();
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

    private void loadItems() {
        String baseURL = "http://10.0.2.2/android/getPlaces.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, baseURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){

                                JSONObject object = array.getJSONObject(i);

                                String name = object.getString("name");
                                String image = object.getString("imageURL");

                                Log.d("image", image);

                                TripPlaces tripPlaces = new TripPlaces(name, image);
                                items.add(tripPlaces);
                            }

                        }catch (Exception e){

                        }

                        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(Menu.this,
                                items);
                        recycler.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Menu.this, error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(Menu.this).add(stringRequest);

    }
=======
        setContentView(R.layout.activity_login);
    }//edit asdfhaskjod
>>>>>>> 7ff1b0d564730cac7fbc0bf28f6f3897f23a32f4

}
