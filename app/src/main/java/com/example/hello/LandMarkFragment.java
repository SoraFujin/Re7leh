package com.example.hello;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
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
import java.util.List;

public class LandMarkFragment extends Fragment {
    private static final String TAG = "LandMarkFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private List<City> cityList;
    private RequestQueue requestQueue;
    private LinearLayout cityContainer;
    private LinearLayout placeContainer;
    private SharedPreferences sharedPreferences;

    // Update this URL to your actual endpoint
    String url = "http://10.0.2.2/android/cities.php";

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
        requestQueue = Volley.newRequestQueue(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_land_mark, container, false);

        cityContainer = view.findViewById(R.id.cityContainer);
        placeContainer = view.findViewById(R.id.placeContainer);
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        Button nextButton = view.findViewById(R.id.Nextbtn);
        nextButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.landMarkfragmentContainer, new TransportFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        });

        fetchCities();
        return view;
    }

    private void fetchCities() {
        Log.d(TAG, "Fetching cities from URL: " + url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Log.d(TAG, "Response received: " + response.toString());
                    try {
                        parseCities(response);
                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing JSON: " + e.getMessage(), e);
                        Toast.makeText(getContext(), "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e(TAG, "Error fetching data: " + error.getMessage(), error);
                    Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void parseCities(JSONArray jsonArray) throws JSONException {
        cityList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int cityID = jsonObject.getInt("cityID");
            String cityName = jsonObject.getString("cityName");
            String cityImagePath = jsonObject.getString("cityImagePath");

            JSONArray landmarksArray = jsonObject.getJSONArray("landmarks");
            List<LandMark> landmarks = new ArrayList<>();

            for (int j = 0; j < landmarksArray.length(); j++) {
                JSONObject landmarkObject = landmarksArray.getJSONObject(j);
                int landmarkID = landmarkObject.getInt("landmarkID");
                String placeName = landmarkObject.getString("place_name");
                String imagePath = landmarkObject.getString("image_path");

                landmarks.add(new LandMark(imagePath, placeName, landmarkID));
            }

            cityList.add(new City(cityImagePath, cityName, landmarks, cityID));
        }

        populateCityScrollView();
    }

    private void populateCityScrollView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        cityContainer.removeAllViews();

        for (City city : cityList) {
            View cityView = inflater.inflate(R.layout.city_item, cityContainer, false);
            ImageView cityImageView = cityView.findViewById(R.id.cityImageView);
            TextView cityTextView = cityView.findViewById(R.id.cityTextView);

            Glide.with(this).load(city.getImageResourcePath()).into(cityImageView);
            cityTextView.setText(city.getCityName());

            cityView.setOnClickListener(v -> {
                saveSelectedCity(city);
                showCityPlaces(city);
            });

            cityContainer.addView(cityView);
        }
    }

    private void showCityPlaces(City city) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        placeContainer.removeAllViews();

        for (LandMark landmark : city.getPlaces()) {
            View placeView = inflater.inflate(R.layout.land_mark, placeContainer, false);
            ImageView placeImageView = placeView.findViewById(R.id.placeImageView);
            TextView placeTextView = placeView.findViewById(R.id.placeTextView);

            Glide.with(this).load(landmark.getImageUrl()).into(placeImageView);
            placeTextView.setText(landmark.getName());

            placeView.setOnClickListener(v -> {
                saveSelectedLandmark(landmark);
                Toast.makeText(getContext(), "Selected: " + landmark.getName(), Toast.LENGTH_SHORT).show();
            });

            placeContainer.addView(placeView);
        }
    }

    private void saveSelectedCity(City city) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedCityName", city.getCityName());
        editor.putInt("cityID", city.getCityID());
        editor.apply();
    }

    private void saveSelectedLandmark(LandMark landmark) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedPlaceName", landmark.getName());
        editor.putInt("placeID", landmark.getPlaceID());
        editor.apply();
    }
}
