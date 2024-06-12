package com.example.hello;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
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

public class TransportFragment extends Fragment {
    private int transportationID;
    LinearLayout transportContainer;
    private RequestQueue queue;
    private SharedPreferences sharedPreferences;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public TransportFragment() {
        // Required empty public constructor
    }

    public static TransportFragment newInstance(String param1, String param2) {
        TransportFragment fragment = new TransportFragment();
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
        View view = inflater.inflate(R.layout.fragment_transport, container, false);

        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        TextView transportTextView = view.findViewById(R.id.transportTextView);
        transportContainer = view.findViewById(R.id.transportContainer);

        fetchTransportation();

        Button nextButton = view.findViewById(R.id.Nextbtn);
        nextButton.setOnClickListener(v -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.landMarkfragmentContainer, HotelFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("name")
                    .commit();
        });

        return view;
    }

    private void fetchTransportation() {
        String url = "http://10.0.2.2/android/get_transportation.php";
        Log.d("url", url);
        queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            parseTransportation(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    private void parseTransportation(JSONArray jsonArray) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject transportObject = jsonArray.getJSONObject(i);
            transportationID = transportObject.getInt("id");
            Log.d("aaassss", String.valueOf(transportationID));
            String transportName = transportObject.getString("type");
            String imageResourceId = transportObject.getString("image_url");

            addTransportOption(transportName, imageResourceId);
        }
    }


    private void addTransportOption(String transportName, String imageResourceId) {
        View transportItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_transportation, transportContainer, false);
        TextView transportItemTextView = transportItemView.findViewById(R.id.transportNameTextView);
        ImageView transportItemImageView = transportItemView.findViewById(R.id.transportImageView);
        transportItemTextView.setText(transportName);
        Glide.with(this).load(imageResourceId).into(transportItemImageView);
        transportItemView.setOnClickListener(v -> {
            saveSelectedTransport(transportName);
            Toast.makeText(getContext(), "Selected transport: " + transportName, Toast.LENGTH_SHORT).show();
        });

        transportContainer.addView(transportItemView);
    }


    private void saveSelectedTransport(String transportName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedTransport", transportName);
        editor.putInt("carID", transportationID);
        Log.d("aaasss", String.valueOf(transportationID));
        editor.apply();
    }
}
