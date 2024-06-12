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

public class AddEditCars extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addeditcars);

        EditText type = findViewById(R.id.typeEditText);
        EditText description = findViewById(R.id.descriptionEditText);
        EditText price = findViewById(R.id.priceEditText);
        EditText imageUrl = findViewById(R.id.imageUrlEditText);

        Button execute = findViewById(R.id.executeButton);
        Intent intent = getIntent();
        int op = intent.getIntExtra("op", 0);
        int idObject = intent.getIntExtra("id",0);
        String typeObject = intent.getStringExtra("type");
        String descriptionObject = intent.getStringExtra("description");
        double priceObject = intent.getDoubleExtra("price",0);
        String imageUrlObject = intent.getStringExtra("imageUrl");

        if(op == 1){
            type.setText(typeObject);
            description.setText(descriptionObject);
            price.setText(String.valueOf(priceObject));
            imageUrl.setText(imageUrlObject);
        }
        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(op == 0){
                    String typeObject2 = type.getText().toString();
                    String descriptionObject2 = description.getText().toString();
                    float costObject2 = Float.parseFloat(price.getText().toString());
                    String imageUrlObject2 = imageUrl.getText().toString();
                    addCars(typeObject2,descriptionObject2,costObject2,imageUrlObject2);
                }else{
                    String typeObject2 = type.getText().toString();
                    String descriptionObject2 = description.getText().toString();
                    float costObject2 = Float.parseFloat(price.getText().toString());
                    String imageUrlObject2 = imageUrl.getText().toString();
                    editCars(idObject,typeObject2,descriptionObject2,costObject2,imageUrlObject2);
                }
                Intent intent = new Intent(AddEditCars.this, ManageCars.class);
                startActivity(intent);
            }
        });

        // Bottom bar
            ImageView managerIcon = findViewById(R.id.manager_icon);
            managerIcon.setVisibility(View.VISIBLE);
            managerIcon.setOnClickListener(e -> {
                Intent newIntent = new Intent(this, Management.class);
                startActivity(intent);
                finish();
            });

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

    private void addCars(String type, String descriptionObject2,float costObject2,String imageUrlObject2){
        String url = "http://10.0.2.2/android/add_cars.php";

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
                params.put("description", descriptionObject2);
                params.put("price", String.valueOf(costObject2));
                params.put("image_url", imageUrlObject2);

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }

    private void editCars(int id,String type, String descriptionObject2,float costObject2,String imageUrlObject2){
        String url = "http://10.0.2.2/android/edit_cars.php";

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
                params.put("id", String.valueOf(id));
                params.put("type", type);
                params.put("description", descriptionObject2);
                params.put("price", String.valueOf(costObject2));
                params.put("image_url", imageUrlObject2);

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }
}
