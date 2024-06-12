package com.example.hello;

import android.os.Bundle;
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

public class CarBooking extends AppCompatActivity {
    private EditText fullNameEditText;
    private EditText mobileNumberEditText;
    private EditText emailEditText;
    private Button bookButton;
    private TextView rentalDatesTextView;

    private int carId;
    private String startDate;
    private String endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_booking);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        bookButton = findViewById(R.id.bookButton);
        rentalDatesTextView = findViewById(R.id.rentalDatesTextView);

        carId = getIntent().getIntExtra("carId", -1);
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");

        rentalDatesTextView.setText("Rental Dates: " + startDate + " - " + endDate);

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEditText.getText().toString().trim();
                String mobileNumber = mobileNumberEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();

                if (fullName.isEmpty() || mobileNumber.isEmpty() || email.isEmpty()) {
                    Toast.makeText(CarBooking.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    bookCar(carId, fullName, mobileNumber, email, startDate, endDate);
                }
            }
        });
    }

    private void bookCar(int carId, String fullName, String mobileNumber, String email, String startDate, String endDate) {
        String url = "http://10.0.2.2/travelapp/car_booking.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            Toast.makeText(CarBooking.this, "Car rental successful", Toast.LENGTH_SHORT).show();
                            if (success) {
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CarBooking.this, "Rental failed.try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("car_id", String.valueOf(carId));
                params.put("full_name", fullName);
                params.put("mobile_number", mobileNumber);
                params.put("email", email);
                params.put("rental_start_date", startDate);
                params.put("rental_end_date", endDate);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }
}