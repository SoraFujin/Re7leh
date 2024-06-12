package com.example.hello;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RestaurantBooking extends AppCompatActivity {
    private EditText fullNameEditText;
    private EditText mobileNumberEditText;
    private EditText emailEditText;
    private Button bookingButton;
    private TextView bookingDateTextView;

    private int restaurantId;
    private String bookingDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurants_booking);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        bookingButton = findViewById(R.id.bookingButton);
        bookingDateTextView = findViewById(R.id.bookingDateTextView);

        restaurantId = getIntent().getIntExtra("restaurantId", -1);
        bookingDate = getIntent().getStringExtra("bookingDate");

        bookingDateTextView.setText("Booking Date: " + bookingDate);

        bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEditText.getText().toString().trim();
                String mobileNumber = mobileNumberEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();

                if (fullName.isEmpty() || mobileNumber.isEmpty() || email.isEmpty()) {
                    Toast.makeText(RestaurantBooking.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    saveBookingToDatabase(restaurantId, fullName, mobileNumber, email, bookingDate);
                }
            }
        });
    }

    private void saveBookingToDatabase(int restaurantId, String fullName, String mobileNumber, String email, String bookingDate) {
        String url = "http://10.0.2.2/travelapp/restaurant_booking.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            String message = jsonObject.getString("message");
                            Toast.makeText(RestaurantBooking.this, message, Toast.LENGTH_SHORT).show();
                            if (success) {
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RestaurantBooking.this, "Error parsing ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                        error.printStackTrace();
                        Toast.makeText(RestaurantBooking.this, "Booking failed. Please try again.", Toast.LENGTH_SHORT).show();
                        // Log the error message
                        Log.e("RestaurantBooking", "Error: " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("restaurant_id", String.valueOf(restaurantId));
                params.put("full_name", fullName);
                params.put("mobile_number", mobileNumber);
                params.put("email", email);
                params.put("booking_date", bookingDate);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }
}