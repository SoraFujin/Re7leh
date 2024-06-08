package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddEditHR extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addedithr);

        EditText name = findViewById(R.id.nameEditText);
        EditText location = findViewById(R.id.locationEditText);
        EditText rating = findViewById(R.id.ratingEditText);
        EditText imageName = findViewById(R.id.imageNameEditText);

        Button execute = findViewById(R.id.executeButton);
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        int op = intent.getIntExtra("op", 0);
        int idObject = intent.getIntExtra("id", 0);
        String nameObject = intent.getStringExtra("name");
        String locationObject = intent.getStringExtra("location");
        float ratingObject = intent.getFloatExtra("rating", 0.0f);
        String imageNameObject = intent.getStringExtra("imageName");

        if(op == 1){
            name.setText(nameObject);
            location.setText(locationObject);
            rating.setText(String.valueOf(ratingObject));
            imageName.setText(imageNameObject);
        }

        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == 0){
                    if(op == 0){
                        String nameHotel2 = name.getText().toString();
                        String locationHotel2 = location.getText().toString();
                        float ratingHotel2 = Float.parseFloat(rating.getText().toString());
                        String imageNameHotel2 = imageName.getText().toString();
                        addHotel(nameHotel2,locationHotel2,ratingHotel2,imageNameHotel2);
                    }else{
                        String nameHotel2 = name.getText().toString();
                        String locationHotel2 = location.getText().toString();
                        float ratingHotel2 = Float.parseFloat(rating.getText().toString());
                        String imageNameHotel2 = imageName.getText().toString();
                        editHotel(idObject,nameHotel2,locationHotel2,ratingHotel2,imageNameHotel2);
                    }
                    Intent intent = new Intent(AddEditHR.this, ManageHotels.class);
                    startActivity(intent);
                }else{
                    if(op == 0){
                        String nameRestaurant2 = name.getText().toString();
                        String locationRestaurant2 = location.getText().toString();
                        float ratingRestaurant2 = Float.parseFloat(rating.getText().toString());
                        String imageNameRestaurant2 = imageName.getText().toString();
                        addRestaurants(nameRestaurant2,locationRestaurant2,ratingRestaurant2,imageNameRestaurant2);
                    }else{
                        String nameRestaurant2 = name.getText().toString();
                        String locationRestaurant2 = location.getText().toString();
                        float ratingRestaurant2 = Float.parseFloat(rating.getText().toString());
                        String imageNameRestaurant2 = imageName.getText().toString();
                        editRestaurants(idObject,nameRestaurant2,locationRestaurant2,ratingRestaurant2,imageNameRestaurant2);
                    }
                    Intent intent = new Intent(AddEditHR.this, ManageRestaurants.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void addHotel(String name, String location, float rating, String imageName){
        String url = "http://10.0.2.2/android/add_hotels.php";

        RequestQueue queue = Volley.newRequestQueue(this);


        // Create a JsonObjectRequest with POST method
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.

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
                params.put("name", name);
                params.put("location", location);
                params.put("rating", String.valueOf(rating));
                params.put("imageName", imageName);

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }

    private void editHotel(int id, String name, String location, float rating, String imageName){
        String url = "http://10.0.2.2/android/edit_hotels.php";

        RequestQueue queue = Volley.newRequestQueue(this);


        // Create a JsonObjectRequest with POST method
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.

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
                params.put("id", String.valueOf(id));
                params.put("name", name);
                params.put("location", location);
                params.put("rating", String.valueOf(rating));
                params.put("imageName", imageName);

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }

    private void addRestaurants(String name, String location, float rating, String imageName){
        String url = "http://10.0.2.2/android/add_restaurants.php";

        RequestQueue queue = Volley.newRequestQueue(this);


        // Create a JsonObjectRequest with POST method
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.

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
                params.put("name", name);
                params.put("location", location);
                params.put("rating", String.valueOf(rating));
                params.put("imageName", imageName);

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }

    private void editRestaurants(int id, String name, String location, float rating, String imageName){
        String url = "http://10.0.2.2/android/edit_restaurants.php";

        RequestQueue queue = Volley.newRequestQueue(this);


        // Create a JsonObjectRequest with POST method
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.

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
                params.put("id", String.valueOf(id));
                params.put("name", name);
                params.put("location", location);
                params.put("rating", String.valueOf(rating));
                params.put("imageName", imageName);

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }

}
