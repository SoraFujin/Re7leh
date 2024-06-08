package com.example.hello;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LandMarkFragment extends Fragment {
    private String selectedCityName;
    private List<LandMark> selectedPlaces;
    private LinearLayout cityContainer;
    private LinearLayout placeContainer;
    private List<City> cityList;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public LandMarkFragment() {
        // Required empty public constructor
    }

    public static LandMarkFragment newInstance(String param1, String param2) {
        LandMarkFragment fragment = new LandMarkFragment();
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
        View view = inflater.inflate(R.layout.fragment_land_mark, container, false);

        cityContainer = view.findViewById(R.id.cityContainer);
        placeContainer = view.findViewById(R.id.placeContainer);

        populateData();
        populateCityScrollView(view);

        Button nextButton = view.findViewById(R.id.Nextbtn);

        return view;
    }

    private void populateData() {
        cityList = new ArrayList<>();

        List<LandMark> bethlahemPlaces = new ArrayList<>();
        bethlahemPlaces.add(new LandMark(R.drawable.beitsahor, "Beit Sahor"));
        bethlahemPlaces.add(new LandMark(R.drawable.catherinechurch, " St. Catherine's Church"));
        bethlahemPlaces.add(new LandMark(R.drawable.churchofnativity, "Church Of Nativity"));
        bethlahemPlaces.add(new LandMark(R.drawable.mangersquare, "Manger Square"));
        bethlahemPlaces.add(new LandMark(R.drawable.milkgrotto, "Milk Grotto"));
        bethlahemPlaces.add(new LandMark(R.drawable.palestinecenter, "Palestinian Heritage Center"));


        List<LandMark> jerusalemPlaces = new ArrayList<>();
        jerusalemPlaces.add(new LandMark(R.drawable.haramelsharif, "Haram Al-Sharif"));
        jerusalemPlaces.add(new LandMark(R.drawable.churchofholy, "Church Of The Holy Sepulchre"));
        jerusalemPlaces.add(new LandMark(R.drawable.mountofolives, "Mount of Olives"));
        jerusalemPlaces.add(new LandMark(R.drawable.muslimqurtur, "Muslim Quarter"));
        jerusalemPlaces.add(new LandMark(R.drawable.towerofdavid, "Tower Of David"));
        jerusalemPlaces.add(new LandMark(R.drawable.wallplaza, "Western Wall Plaza"));

        List<LandMark> ramallahPlaces = new ArrayList<>();
        ramallahPlaces.add(new LandMark(R.drawable.manarah, "Al Manarah"));
        ramallahPlaces.add(new LandMark(R.drawable.mandela, "Mandela Square"));
        ramallahPlaces.add(new LandMark(R.drawable.bzumuseum, "Birzeit University Museum"));
        ramallahPlaces.add(new LandMark(R.drawable.darwishmeusim, "Darwish Museum"));
        ramallahPlaces.add(new LandMark(R.drawable.arafatmeusem, "Arafat Museum"));
        ramallahPlaces.add(new LandMark(R.drawable.khirbeteltireh, "Khirbet al Tireh"));
        ramallahPlaces.add(new LandMark(R.drawable.oldcity, "The Old City"));

        List<LandMark> jerichoPlaces = new ArrayList<>();
        jerichoPlaces.add(new LandMark(R.drawable.wadiqekt, "Wadi Qelt"));
        jerichoPlaces.add(new LandMark(R.drawable.georgejercho, "St. George's Monastery"));
        jerichoPlaces.add(new LandMark(R.drawable.hishampalace, "Hisham's Palace"));
        jerichoPlaces.add(new LandMark(R.drawable.jerichiotell, "Jericho Tell"));
        jerichoPlaces.add(new LandMark(R.drawable.nabimopsa, "Nabi Musa"));
        jerichoPlaces.add(new LandMark(R.drawable.mountoftemtation, "Mount of Temptation"));

        List<LandMark> nablusPlaces = new ArrayList<>();
        nablusPlaces.add(new LandMark(R.drawable.balatareligious, "Balata Religious Sites"));
        nablusPlaces.add(new LandMark(R.drawable.jenin, "Jenin"));
        nablusPlaces.add(new LandMark(R.drawable.palladio, "The Palladio (House of Palestine)"));
        nablusPlaces.add(new LandMark(R.drawable.mountgerizim, "Mount Gerizim"));
        nablusPlaces.add(new LandMark(R.drawable.sebastya, "Sebastiya (Ancient Samaria)"));
        nablusPlaces.add(new LandMark(R.drawable.oldcitynablus, "Old City"));

        cityList.add(new City(R.drawable.bethlehem, "Bethlehem", bethlahemPlaces));
        cityList.add(new City(R.drawable.jerusalem,"Jerusalem", jerusalemPlaces));
        cityList.add(new City(R.drawable.ramallah, "Ramallah", ramallahPlaces));
        cityList.add(new City(R.drawable.jericho, "Jericho", jerichoPlaces));
        cityList.add(new City(R.drawable.nablus,"Nablus", nablusPlaces));
    }

    private void populateCityScrollView(View rootView) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout cityScrollView = rootView.findViewById(R.id.cityContainer);

        for (City city : cityList) {
            View cityView = inflater.inflate(R.layout.city_item, cityScrollView, false);
            ImageView cityImageView = cityView.findViewById(R.id.cityImageView);
            cityImageView.setImageResource(city.getImageResourceId());
            TextView cityTextView = cityView.findViewById(R.id.cityTextView);
            cityTextView.setText(city.getCityName());

            cityView.setOnClickListener(v -> updatePlaceScrollView(city.getPlaces()));
            cityScrollView.addView(cityView);
        }
    }

    private void updatePlaceScrollView(List<LandMark> places) {
        placeContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (LandMark place : places) {
            View placeView = inflater.inflate(R.layout.land_mark, placeContainer, false);
            ImageView placeImageView = placeView.findViewById(R.id.placeImageView);
            TextView placeTextView = placeView.findViewById(R.id.PlaceTextView);
            placeTextView.setText(place.getName());
            placeImageView.setImageResource(place.getImageResourceId());
            placeContainer.addView(placeView);
        }
    }


}
