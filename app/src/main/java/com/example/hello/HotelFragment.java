package com.example.hello;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class HotelFragment extends Fragment {

    private ArrayList<LandMark> selectedHotels;
    private String selectedCityName;
    private SharedPreferences sharedPreferences;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);


        sharedPreferences = getActivity().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);


        selectedCityName = sharedPreferences.getString("selectedCity", "");

        if (selectedCityName != null) {

            selectedHotels = getSelectedHotels(selectedCityName);


            LinearLayout hotelContainer = view.findViewById(R.id.hotelContainer);


            if (hotelContainer != null) {

                for (LandMark hotel : selectedHotels) {

                    View hotelView = inflater.inflate(R.layout.hotel_item, hotelContainer, false);


                    ImageView hotelImageView = hotelView.findViewById(R.id.hotelImageView);
                    hotelImageView.setImageResource(hotel.getImageResourceId());


                    hotelContainer.addView(hotelView);
                }
            }
        }

        return view;
    }



    private ArrayList<LandMark> getSelectedHotels(String selectedCityName) {

        ArrayList<LandMark> hotels = new ArrayList<>();

        if (selectedCityName.equals("Bethlehem")) {
            hotels.add(new LandMark(R.drawable.abrahams, "Abrahams Herberge - Beit Ibrahem"));
            hotels.add(new LandMark(R.drawable.grandhotelbethlahem, "Grand Hotel Bethlehem"));
            hotels.add(new LandMark(R.drawable.lotushotel, "Lotus Boutique Hotel"));
            hotels.add(new LandMark(R.drawable.bethlehem, "Bethlehem Hotel"));
            hotels.add(new LandMark(R.drawable.saintmichael, "Saint Michael Hotel"));
            hotels.add(new LandMark(R.drawable.assarayahotel, "Assaraya Palace Hotel"));

        } else if (selectedCityName.equals("Jerusalem")) {
            hotels.add(new LandMark(R.drawable.jerusalem, "Hotel 3"));
            hotels.add(new LandMark(R.drawable.jerusalem, "Hotel 4"));

        }

        return hotels;
    }
}
