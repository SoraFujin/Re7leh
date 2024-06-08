package com.example.hello;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Management extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);

        setSpinner();
        setMenuItems();
    }

    private void setMenuItems(){
        LinearLayout tour = findViewById(R.id.menu_option_layout1);
        LinearLayout hotel = findViewById(R.id.menu_option_layout2);
        LinearLayout food = findViewById(R.id.menu_option_layout3);
        LinearLayout transport = findViewById(R.id.menu_option_layout4);

        tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Management.this, ManageTour.class);
                startActivity(intent);
            }
        });

        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Management.this, ManageHotels.class);
                startActivity(intent);
            }
        });

        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Management.this, ManageRestaurants.class);
                startActivity(intent);
            }
        });

        transport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Management.this, ManageTransport.class);
                startActivity(intent);
            }
        });

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

}
