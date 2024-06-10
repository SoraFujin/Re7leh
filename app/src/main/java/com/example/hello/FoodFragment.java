package com.example.hello;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.ArrayList;

public class FoodFragment extends Fragment {
    private TextView resturantTextView;

    private ArrayList<LandMark> selectedRestaurants;
    private String selectedCityName;
    private SharedPreferences sharedPreferences;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        resturantTextView = view.findViewById(R.id.ResturantTextView);
        String cityName = sharedPreferences.getString("selectedCityName", "");
        String restaurants = "Restaurants in: " + (cityName.isEmpty() ? "Where would you like to dine" : cityName);
        resturantTextView.setText(restaurants);

        if (sharedPreferences != null) {
            selectedCityName = sharedPreferences.getString("selectedCityName", "");

            if (!selectedCityName.isEmpty()) {
                selectedRestaurants = getSelectedRestaurants(selectedCityName);
            } else {
                selectedRestaurants = getAllRestaurants();
            }

            LinearLayout restaurantContainer = view.findViewById(R.id.resturantContainer);

            if (restaurantContainer != null) {
                for (LandMark restaurant : selectedRestaurants) {
                    View restaurantView = inflater.inflate(R.layout.hotel_item, restaurantContainer, false);

                    ImageView restaurantImageView = restaurantView.findViewById(R.id.hotelImageView);
                    restaurantImageView.setImageResource(restaurant.getImageResourceId());

                    TextView restaurantNameTextView = restaurantView.findViewById(R.id.hotelNameTextView);
                    restaurantNameTextView.setText(restaurant.getName());

                    restaurantView.setOnClickListener(v -> {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("selectedRestaurantName", restaurant.getName());
                        editor.apply();
                        Toast.makeText(getContext(), "Selected place: " + restaurant.getName(), Toast.LENGTH_SHORT).show();
                    });

                    restaurantContainer.addView(restaurantView);
                }
            }
        }

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

    private ArrayList<LandMark> getSelectedRestaurants(String selectedCityName) {

        ArrayList<LandMark> restaurants = new ArrayList<>();

        switch (selectedCityName) {
            case "Bethlehem":
                restaurants.add(new LandMark(R.drawable.anthonyspizaa, "Anthonys Pizza"));
                restaurants.add(new LandMark(R.drawable.alsufararesturant, "Al Sufara Resturant"));
                restaurants.add(new LandMark(R.drawable.tabooresturant, "Taboo Burger Bar"));
                restaurants.add(new LandMark(R.drawable.ilforo, "Il Forno"));
                restaurants.add(new LandMark(R.drawable.askadinya, "Askadinya"));
                restaurants.add(new LandMark(R.drawable.jalajungle, "Jala Jungle"));
                break;
            case "Jerusalem":
                restaurants.add(new LandMark(R.drawable.anthonyspizaa, "Anthonys Pizza"));
                restaurants.add(new LandMark(R.drawable.sarwastreetkitchen, "Sarwa Street Kitchen"));
                restaurants.add(new LandMark(R.drawable.dejabu, "Deja Bu"));
                restaurants.add(new LandMark(R.drawable.pergamon, "Pergamon Resturant"));
                restaurants.add(new LandMark(R.drawable.katy, "Katy's"));
                restaurants.add(new LandMark(R.drawable.beerbazaar, "BeerBazaar Jerusalem"));
                break;
            case "Ramallah":
                restaurants.add(new LandMark(R.drawable.zest, "Zest Resturant"));
                restaurants.add(new LandMark(R.drawable.snowbar, "SnowBar Garden: Pool, Resturant & Bar"));
                restaurants.add(new LandMark(R.drawable.darna, "Darna Resturant"));
                restaurants.add(new LandMark(R.drawable.cafelevie, "Cafe La Vie"));
                restaurants.add(new LandMark(R.drawable.azure, "Azure Resturant"));
                restaurants.add(new LandMark(R.drawable.bahriresturant, "Bahri Resturant"));
                restaurants.add(new LandMark(R.drawable.lazaward, "Lazaward"));
                break;

            case "Jericho":
                restaurants.add(new LandMark(R.drawable.mecasa, "Me Casa Resturant"));
                restaurants.add(new LandMark(R.drawable.limona, "Limona"));
                restaurants.add(new LandMark(R.drawable.lowestbarintheworld, "Lowest Bar in The World"));
                restaurants.add(new LandMark(R.drawable.cafejerico, "21Cafe Jericho"));
                restaurants.add(new LandMark(R.drawable.moonlightrest, "Moon Light Rest House"));
                restaurants.add(new LandMark(R.drawable.sultanaresturant, "Sultana Resturant"));
                break;

            case "Nablus":
                restaurants.add(new LandMark(R.drawable.alflaykat, "Mateam Alf Laylat W Laylat - Nabulus"));
                restaurants.add(new LandMark(R.drawable.noshacafe, "Nosha Cafe"));
                restaurants.add(new LandMark(R.drawable.burgerspace, "Burger Space"));
                restaurants.add(new LandMark(R.drawable.alkonisweets, "Al Koni Sweets"));
                restaurants.add(new LandMark(R.drawable.orgada, "Orgada Burgers"));
                restaurants.add(new LandMark(R.drawable.cedarz, "Cedarz Gelato & Coffe House"));
                break;
        }

        return restaurants;
    }

    private ArrayList<LandMark> getAllRestaurants() {
        ArrayList<LandMark> restaurants = new ArrayList<>();

        restaurants.add(new LandMark(R.drawable.anthonyspizaa, "Anthonys Pizza"));
        restaurants.add(new LandMark(R.drawable.alsufararesturant, "Al Sufara Resturant"));
        restaurants.add(new LandMark(R.drawable.tabooresturant, "Taboo Burger Bar"));
        restaurants.add(new LandMark(R.drawable.ilforo, "Il Forno"));
        restaurants.add(new LandMark(R.drawable.askadinya, "Askadinya"));
        restaurants.add(new LandMark(R.drawable.jalajungle, "Jala Jungle"));

        restaurants.add(new LandMark(R.drawable.anthonyspizaa, "Anthonys Pizza"));
        restaurants.add(new LandMark(R.drawable.sarwastreetkitchen, "Sarwa Street Kitchen"));
        restaurants.add(new LandMark(R.drawable.dejabu, "Deja Bu"));
        restaurants.add(new LandMark(R.drawable.pergamon, "Pergamon Resturant"));
        restaurants.add(new LandMark(R.drawable.katy, "Katy's"));
        restaurants.add(new LandMark(R.drawable.beerbazaar, "BeerBazaar Jerusalem"));

        restaurants.add(new LandMark(R.drawable.zest, "Zest Resturant"));
        restaurants.add(new LandMark(R.drawable.snowbar, "SnowBar Garden: Pool, Resturant & Bar"));
        restaurants.add(new LandMark(R.drawable.darna, "Darna Resturant"));
        restaurants.add(new LandMark(R.drawable.cafelevie, "Cafe La Vie"));
        restaurants.add(new LandMark(R.drawable.azure, "Azure Resturant"));
        restaurants.add(new LandMark(R.drawable.bahriresturant, "Bahri Resturant"));
        restaurants.add(new LandMark(R.drawable.lazaward, "Lazaward"));

        restaurants.add(new LandMark(R.drawable.mecasa, "Me Casa Resturant"));
        restaurants.add(new LandMark(R.drawable.limona, "Limona"));
        restaurants.add(new LandMark(R.drawable.lowestbarintheworld, "Lowest Bar in The World"));
        restaurants.add(new LandMark(R.drawable.cafejerico, "21Cafe Jericho"));
        restaurants.add(new LandMark(R.drawable.moonlightrest, "Moon Light Rest House"));
        restaurants.add(new LandMark(R.drawable.sultanaresturant, "Sultana Resturant"));

        restaurants.add(new LandMark(R.drawable.alflaykat, "Mateam Alf Laylat W Laylat - Nabulus"));
        restaurants.add(new LandMark(R.drawable.noshacafe, "Nosha Cafe"));
        restaurants.add(new LandMark(R.drawable.burgerspace, "Burger Space"));
        restaurants.add(new LandMark(R.drawable.alkonisweets, "Al Koni Sweets"));
        restaurants.add(new LandMark(R.drawable.orgada, "Orgada Burgers"));
        restaurants.add(new LandMark(R.drawable.cedarz, "Cedarz Gelato & Coffe House"));

        return restaurants;
    }

}
