package com.example.hello;

import static android.content.Intent.getIntent;

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

public class BookingDetailsActivity extends AppCompatActivity {
    private EditText fullNameEditText;
    private EditText mobileNumberEditText;
    private EditText emailEditText;
    private Button reserveButton;

    private String startDate;
    private String endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_details);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        emailEditText = findViewById(R.id.emailEditText);
        reserveButton = findViewById(R.id.reserveButton);
        startDate = getIntent().getStringExtra("startDate");
        endDate = getIntent().getStringExtra("endDate");


        TextView selectedDatesTextView = findViewById(R.id.selectedDatesTextView);
        selectedDatesTextView.setText("Selected Dates: " + startDate + " - " + endDate);



        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEditText.getText().toString().trim();
                String mobileNumber = mobileNumberEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();

                if (fullName.isEmpty() || mobileNumber.isEmpty() || email.isEmpty()) {
                    Toast.makeText(BookingDetailsActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    saveBookingToDatabase(fullName, mobileNumber, email, startDate, endDate);
                }
            }
        });
    }

    private void saveBookingToDatabase(String fullName, String mobileNumber, String email, String startDate, String endDate) {
        String url = "http://10.0.2.2/travelapp/save_booking.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            Toast.makeText(BookingDetailsActivity.this, "room has been reserved for " + fullName + " " + "succseefully"  , Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(BookingDetailsActivity.this, "Booking failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("full_name", fullName);
                params.put("mobile_number", mobileNumber);
                params.put("email", email);
                params.put("start_date", startDate);
                params.put("end_date", endDate);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }
}