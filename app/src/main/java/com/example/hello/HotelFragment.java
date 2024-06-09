package com.example.hello;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HotelFragment extends Fragment {
    private TextView cityTextView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);

        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        TextView cityTextView = view.findViewById(R.id.CityTextView);
        String cityName = sharedPreferences.getString("selectedCityName", "");
        String hotels = "Hotels in: " + (cityName.isEmpty() ? "Where would you like to stay" : cityName);
        cityTextView.setText(hotels);

        if (sharedPreferences != null) {
            selectedCityName = sharedPreferences.getString("selectedCityName", "");

            if (!selectedCityName.isEmpty()) {
                selectedHotels = getSelectedHotels(selectedCityName);
            } else {
                selectedHotels = getAllHotels();
            }

            LinearLayout hotelContainer = view.findViewById(R.id.hotelContainer);

            if (hotelContainer != null) {
                for (LandMark hotel : selectedHotels) {
                    View hotelView = inflater.inflate(R.layout.hotel_item, hotelContainer, false);

                    ImageView hotelImageView = hotelView.findViewById(R.id.hotelImageView);
                    hotelImageView.setImageResource(hotel.getImageResourceId());

                    TextView hotelNameTextView = hotelView.findViewById(R.id.hotelNameTextView);
                    hotelNameTextView.setText(hotel.getName());

                    hotelView.setOnClickListener(v -> {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("selectedHotelName", hotel.getName());
                        editor.apply();
                    });

                    hotelContainer.addView(hotelView);
                }
            }
        }

        return view;
    }

    private ArrayList<LandMark> getSelectedHotels(String selectedCityName) {

        ArrayList<LandMark> hotels = new ArrayList<>();

        switch (selectedCityName) {
            case "Bethlehem":
                hotels.add(new LandMark(R.drawable.abrahams, "Abrahams Herberge - Beit Ibrahem"));
                hotels.add(new LandMark(R.drawable.grandhotelbethlahem, "Grand Hotel Bethlehem"));
                hotels.add(new LandMark(R.drawable.lotushotel, "Lotus Boutique Hotel"));
                hotels.add(new LandMark(R.drawable.bethlehem, "Bethlehem Hotel"));
                hotels.add(new LandMark(R.drawable.saintmichael, "Saint Michael Hotel"));
                hotels.add(new LandMark(R.drawable.assarayahotel, "Assaraya Palace Hotel"));

                break;
            case "Jerusalem":
                hotels.add(new LandMark(R.drawable.eldanhotel, "Eldan Hotel"));
                hotels.add(new LandMark(R.drawable.ladystern, "Lady Stern Jerusalem Hotel"));
                hotels.add(new LandMark(R.drawable.ramatrachel, "Ramat Rachel Resort"));
                hotels.add(new LandMark(R.drawable.grandcourthotel, "Grand Court Hotel"));
                hotels.add(new LandMark(R.drawable.jerusalemgate, "Jerusalem Gate Hotel"));
                hotels.add(new LandMark(R.drawable.kingdavid, "King David Hotel Jerusalem"));

                break;
            case "Ramallah":
                hotels.add(new LandMark(R.drawable.ankarshotel, "Ankars Suites & Hotel"));
                hotels.add(new LandMark(R.drawable.millennium, "Millennium Palestine Ramallah"));
                hotels.add(new LandMark(R.drawable.caesarhotel, "Caesar Hotel Ramallah"));
                hotels.add(new LandMark(R.drawable.mirador, "Mirador Hotel"));
                hotels.add(new LandMark(R.drawable.royalhotel, "Royal Court Hotel"));
                hotels.add(new LandMark(R.drawable.carmel, "Carmel Hotel"));

                break;
            case "Jericho":
                hotels.add(new LandMark(R.drawable.auberg, "Auberg-Inn Guesthouse"));
                hotels.add(new LandMark(R.drawable.samihotel, "Sami Hostel"));
                hotels.add(new LandMark(R.drawable.villastar, "Villa Star"));
                hotels.add(new LandMark(R.drawable.jerichoresortvillage, "Jericho Resort Village"));
                hotels.add(new LandMark(R.drawable.qashvillas, "Qash Villas"));
                hotels.add(new LandMark(R.drawable.kareemvilla, "Kareem Villa"));

                break;
            case "Nablus":
                hotels.add(new LandMark(R.drawable.royalsuits, "Royal Suites"));
                hotels.add(new LandMark(R.drawable.saleemafandi, "Saleem Afandi Hotel"));
                hotels.add(new LandMark(R.drawable.israhotel, "Isra Hotel"));
                hotels.add(new LandMark(R.drawable.orienthotel, "Orient Hotel"));
                hotels.add(new LandMark(R.drawable.khanalwakala, "Khan Alwakala Hotel"));
                hotels.add(new LandMark(R.drawable.ycc, "YCC Guesthouse"));

                break;
        }

        return hotels;
    }

    private ArrayList<LandMark> getAllHotels() {
        ArrayList<LandMark> hotels = new ArrayList<>();
        hotels.add(new LandMark(R.drawable.abrahams, "Abrahams Herberge - Beit Ibrahem"));
        hotels.add(new LandMark(R.drawable.grandhotelbethlahem, "Grand Hotel Bethlehem"));
        hotels.add(new LandMark(R.drawable.lotushotel, "Lotus Boutique Hotel"));
        hotels.add(new LandMark(R.drawable.bethlehem, "Bethlehem Hotel"));
        hotels.add(new LandMark(R.drawable.saintmichael, "Saint Michael Hotel"));
        hotels.add(new LandMark(R.drawable.assarayahotel, "Assaraya Palace Hotel"));

        hotels.add(new LandMark(R.drawable.eldanhotel, "Eldan Hotel"));
        hotels.add(new LandMark(R.drawable.ladystern, "Lady Stern Jerusalem Hotel"));
        hotels.add(new LandMark(R.drawable.ramatrachel, "Ramat Rachel Resort"));
        hotels.add(new LandMark(R.drawable.grandcourthotel, "Grand Court Hotel"));
        hotels.add(new LandMark(R.drawable.jerusalemgate, "Jerusalem Gate Hotel"));
        hotels.add(new LandMark(R.drawable.kingdavid, "King David Hotel Jerusalem"));

        hotels.add(new LandMark(R.drawable.ankarshotel, "Ankars Suites & Hotel"));
        hotels.add(new LandMark(R.drawable.millennium, "Millennium Palestine Ramallah"));
        hotels.add(new LandMark(R.drawable.caesarhotel, "Caesar Hotel Ramallah"));
        hotels.add(new LandMark(R.drawable.mirador, "Mirador Hotel"));
        hotels.add(new LandMark(R.drawable.royalhotel, "Royal Court Hotel"));
        hotels.add(new LandMark(R.drawable.carmel, "Carmel Hotel"));

        hotels.add(new LandMark(R.drawable.auberg, "Auberg-Inn Guesthouse"));
        hotels.add(new LandMark(R.drawable.samihotel, "Sami Hostel"));
        hotels.add(new LandMark(R.drawable.villastar, "Villa Star"));
        hotels.add(new LandMark(R.drawable.jerichoresortvillage, "Jericho Resort Village"));
        hotels.add(new LandMark(R.drawable.qashvillas, "Qash Villas"));
        hotels.add(new LandMark(R.drawable.kareemvilla, "Kareem Villa"));

        hotels.add(new LandMark(R.drawable.royalsuits, "Royal Suites"));
        hotels.add(new LandMark(R.drawable.saleemafandi, "Saleem Afandi Hotel"));
        hotels.add(new LandMark(R.drawable.israhotel, "Isra Hotel"));
        hotels.add(new LandMark(R.drawable.orienthotel, "Orient Hotel"));
        hotels.add(new LandMark(R.drawable.khanalwakala, "Khan Alwakala Hotel"));
        hotels.add(new LandMark(R.drawable.ycc, "YCC Guesthouse"));

        return hotels;
    }

}
