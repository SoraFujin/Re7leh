package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddEditTransport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addedittransport);

        EditText type = findViewById(R.id.typeEditText);
        EditText cost = findViewById(R.id.costEditText);

        Button execute = findViewById(R.id.executeButton);
        Intent intent = getIntent();
        int op = intent.getIntExtra("op", 0);
        String typeObject = intent.getStringExtra("type");
        float costObject = intent.getFloatExtra("cost", 0.0f);

        if(op == 1){
            type.setText(typeObject);
            type.setEnabled(false);
            cost.setText(String.valueOf(costObject));
        }
        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(op == 0){
                    String typeObject2 = type.getText().toString();
                    float costObject2 = Float.parseFloat(cost.getText().toString());
                    addTransport(typeObject2,costObject2);
                }else{
                    float costObject2 = Float.parseFloat(cost.getText().toString());
                    editTransport(typeObject,costObject2);
                }
                Intent intent = new Intent(AddEditTransport.this, ManageTransport.class);
                startActivity(intent);
            }
        });

        // Bottom bar
        if (Menu.manager) {
            ImageView managerIcon = findViewById(R.id.manager_icon);
            managerIcon.setVisibility(View.VISIBLE);
            managerIcon.setOnClickListener(e -> {
                Intent newIntent = new Intent(this, Management.class);
                startActivity(intent);
                finish();
            });
        }

        ImageView homeIcon = findViewById(R.id.home_icon);
        homeIcon.setOnClickListener(e -> {
            Intent newIntent = new Intent(this, Menu.class);
            startActivity(intent);
            finish();
        });

        ImageView reminderIcon = findViewById(R.id.notification_icon);
        reminderIcon.setOnClickListener(e -> {
            Intent newIntent = new Intent(this, ReminderActivity.class);
            startActivity(intent);
            finish();
        });

        ImageView favouriteIcon = findViewById(R.id.favorites_icon);
        favouriteIcon.setOnClickListener(e -> {
            Intent newIntent = new Intent(this, WishlistActivity.class);
            startActivity(intent);
            finish();
        });

    }

    private void addTransport(String type, float cost){
        String url = "http://10.0.2.2/android/add_transport.php";

        RequestQueue queue = Volley.newRequestQueue(this);


        // Create a JsonObjectRequest with POST method
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("responseTran",response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Log.d("errorTran",error.toString());
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
                params.put("type", type);
                params.put("cost", String.valueOf(cost));

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }

    private void editTransport(String type, float cost){
        String url = "http://10.0.2.2/android/edit_transport.php";

        RequestQueue queue = Volley.newRequestQueue(this);


        // Create a JsonObjectRequest with POST method
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response1Tran",response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Log.d("error1Tran",error.toString());
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
                params.put("type", type);
                params.put("cost", String.valueOf(cost));

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }
}
