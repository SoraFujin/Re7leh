package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddEditTour extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addedittour);

        EditText tournameText = findViewById(R.id.tourNameEditText);
        EditText destinationText = findViewById(R.id.destinationEditText);
        EditText users_idText = findViewById(R.id.usersIDEditText);
        EditText hotels_idText = findViewById(R.id.hotelsIDEditText);
        EditText restaurants_idText = findViewById(R.id.restaurantsIDEditText);
        EditText transport_idText = findViewById(R.id.transportIDEditText);

        Button execute = findViewById(R.id.executeButton);
        Intent intent = getIntent();
        int op = intent.getIntExtra("op", 0);
        int id = intent.getIntExtra("id", 0);
        String tourname = intent.getStringExtra("tourname");
        String destination = intent.getStringExtra("destination");
        int users_id = intent.getIntExtra("users_id", 0);
        int hotels_id = intent.getIntExtra("hotels_id", 0);
        int restaurants_id = intent.getIntExtra("restaurants_id", 0);
        String transport_id = intent.getStringExtra("transport_id");

        if(op == 1){
            tournameText.setText(tourname);
            destinationText.setText(destination);
            users_idText.setText(String.valueOf(users_id));
            hotels_idText.setText(String.valueOf(hotels_id));
            restaurants_idText.setText(String.valueOf(restaurants_id));
            transport_idText.setText(transport_id);
        }

        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(op == 0){
                    String tourname2 = tournameText.getText().toString();
                    String destination2 = destinationText.getText().toString();
                    int users_id2 = Integer.parseInt(users_idText.getText().toString());
                    int hotels_id2 = Integer.parseInt(hotels_idText.getText().toString());
                    int restaurants_id2 = Integer.parseInt(restaurants_idText.getText().toString());
                    String transport_id2 = transport_idText.getText().toString();
                    addTour(tourname2,destination2,users_id2,hotels_id2,restaurants_id2,transport_id2);
                }else{
                    String tourname2 = tournameText.getText().toString();
                    String destination2 = destinationText.getText().toString();
                    int users_id2 = Integer.parseInt(users_idText.getText().toString());
                    int hotels_id2 = Integer.parseInt(hotels_idText.getText().toString());
                    int restaurants_id2 = Integer.parseInt(restaurants_idText.getText().toString());
                    String transport_id2 = transport_idText.getText().toString();
                    editTour(id,tourname2,destination2,users_id2,hotels_id2,restaurants_id2,transport_id2);
                }
                Intent intent = new Intent(AddEditTour.this, ManageTour.class);
                startActivity(intent);
            }
        });

    }

    private void addTour(String tourName, String destination, int usersId, int hotelsId, int restaurantsId, String transportId){
        String url = "http://10.0.2.2/android/add_tours.php";

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
                params.put("tourName", tourName);
                params.put("destination", destination);
                params.put("usersId", String.valueOf(usersId));
                params.put("hotelsId", String.valueOf(hotelsId));
                params.put("restaurantsId", String.valueOf(restaurantsId));
                params.put("transportId", transportId);

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }

    private void editTour(int id, String tourName, String destination, int usersId, int hotelsId, int restaurantsId, String transportId){
        String url = "http://10.0.2.2/android/edit_tours.php";

        RequestQueue queue = Volley.newRequestQueue(this);


        // Create a JsonObjectRequest with POST method
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseTour",response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Log.d("errorTour",error.toString());
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
                params.put("tourName", tourName);
                params.put("destination", destination);
                params.put("usersId", String.valueOf(usersId));
                params.put("hotelsId", String.valueOf(hotelsId));
                params.put("restaurantsId", String.valueOf(restaurantsId));
                params.put("transportId", transportId);

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }
}
