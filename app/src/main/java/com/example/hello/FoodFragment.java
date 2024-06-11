package com.example.hello;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodFragment extends Fragment {
    private ArrayList<LandMark> selectedRestaurants;
    private String cityName;
    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private String url = "http://10.0.2.2/android/getResturants.php";

    public FoodFragment() {
        // Required empty public constructor
    }

    public static FoodFragment newInstance(String param1, String param2) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        requestQueue = Volley.newRequestQueue(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        TextView restaurantTextView = view.findViewById(R.id.RestaurantTextView);
        cityName = sharedPreferences.getString("selectedCityName", "");
        String restaurants = "Restaurants in: " + (cityName.isEmpty() ? "Where would you like to dine" : cityName);
        restaurantTextView.setText(restaurants);

        fetchRestaurants();

        Button nextButton = view.findViewById(R.id.Nextbtn);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.landMarkfragmentContainer, HotelFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        return view;
    }

    private void fetchRestaurants() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            parseRestaurants(response);
                        } catch (JSONException e) {

                            Toast.makeText(getContext(), "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void parseRestaurants(JSONArray jsonArray) throws JSONException {
        selectedRestaurants = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String restaurantName = jsonObject.getString("name");
            String imageUrl = jsonObject.getString("imageUrl");
            String cityName = jsonObject.getString("cityName");

            selectedRestaurants.add(new LandMark(imageUrl, restaurantName, cityName));
        }
        populateRestaurantViews();
    }

    private void populateRestaurantViews() {
        LinearLayout restaurantContainer = getView().findViewById(R.id.restaurantContainer);
        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (LandMark restaurant : selectedRestaurants) {
            if(restaurant.getCityName().equals(cityName)){
                View restaurantView = inflater.inflate(R.layout.resturant_item, restaurantContainer, false);
                ImageView restaurantImageView = restaurantView.findViewById(R.id.resturantImageView);
                Glide.with(this).load(restaurant.getImageUrl()).into(restaurantImageView);
                TextView restaurantNameTextView = restaurantView.findViewById(R.id.resturantNameTextView);
                restaurantNameTextView.setText(restaurant.getName());
                restaurantView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveSelectedRestaurantName(restaurant.getName());
                        Toast.makeText(getContext(), "Selected: " + restaurant.getName(), Toast.LENGTH_SHORT).show();
                    }
                });

                restaurantContainer.addView(restaurantView);
            }
        }
    }

    private void saveSelectedRestaurantName(String restaurantName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SelectedRestaurantName", restaurantName);
        editor.apply();
    }
}


