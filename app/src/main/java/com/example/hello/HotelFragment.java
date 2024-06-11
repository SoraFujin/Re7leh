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

public class HotelFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private String cityName;
    private ArrayList<LandMark> selectedHotels;
    private RequestQueue requestQueue;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String url = "http://10.0.2.2/android/getHotels.php";

    public HotelFragment() {
        // Required empty public constructor
    }

    public static HotelFragment newInstance(String param1, String param2) {
        HotelFragment fragment = new HotelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);

        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        TextView cityTextView = view.findViewById(R.id.HotelTextView);
        cityName = sharedPreferences.getString("selectedCityName", "");
        String hotels = "Hotels in: " + (cityName.isEmpty() ? "Where would you like to stay" : cityName);
        cityTextView.setText(hotels);

        Button nextButton = view.findViewById(R.id.Nextbtn);
        nextButton.setEnabled(false);

        fetchHotels();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.landMarkfragmentContainer, LandMarkFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        return view;
    }

    private void fetchHotels() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            parseHotels(response);
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

    private void parseHotels(JSONArray jsonArray) throws JSONException {
        selectedHotels = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String hotelName = jsonObject.optString("name", "");
            String hotelImage = jsonObject.optString("imageUrl", "");
            String cityName = jsonObject.optString("cityName", "");
            selectedHotels.add(new LandMark(hotelImage, hotelName, cityName));
        }
        populateHotelViews();
    }

    private void populateHotelViews() {
        View view = getView();
        if (view != null) {
            LinearLayout hotelContainer = view.findViewById(R.id.hotelContainer);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            for (LandMark hotel : selectedHotels) {
                if (hotel.getCityName().equals(cityName)) {
                    View hotelView = inflater.inflate(R.layout.hotel_item, hotelContainer, false);
                    ImageView hotelImageView = hotelView.findViewById(R.id.hotelImageView);
                    Glide.with(this).load(hotel.getImageUrl()).into(hotelImageView);
                    TextView hotelNameTextView = hotelView.findViewById(R.id.hotelNameTextView);
                    hotelNameTextView.setText(hotel.getName());
                    hotelView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveSelectedHotelName(hotel.getName());
                            Toast.makeText(getContext(), "Selected: " + hotel.getName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    hotelContainer.addView(hotelView);
                }
            }
            Button nextButton = view.findViewById(R.id.Nextbtn);
            nextButton.setEnabled(true);
        }
    }

    private void saveSelectedHotelName(String hotelName) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("SelectedHotelName", hotelName);
            editor.apply();
        } else {
            Log.e("HotelFragment", "SharedPreferences is not initialized");
        }
    }
}
