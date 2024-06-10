package com.example.hello;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransportFragment extends Fragment {

    private SharedPreferences sharedPreferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transport, container, false);

        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        TextView transportTextView = view.findViewById(R.id.transportTextView);

        CardView taxiCardView = view.findViewById(R.id.TaxiCardView);
        taxiCardView.setOnClickListener(v -> {
            saveSelectedTransport("Taxi");
            Toast.makeText(getContext(), "Selected transport: Taxi", Toast.LENGTH_SHORT).show();
        });

        CardView busCardView = view.findViewById(R.id.BusCardView);
        busCardView.setOnClickListener(v -> {
            saveSelectedTransport("Bus");
            Toast.makeText(getContext(), "Selected transport: Bus", Toast.LENGTH_SHORT).show();
        });

        CardView uberCardView = view.findViewById(R.id.UberCardView);
        uberCardView.setOnClickListener(v -> {
            saveSelectedTransport("Uber");
            Toast.makeText(getContext(), "Selected transport: Uber", Toast.LENGTH_SHORT).show();
        });

        Button nextButton = view.findViewById(R.id.Nextbtn);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.landMarkfragmentContainer, FoodFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });
        return view;
    }
    private void saveSelectedTransport(String transportName) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedTransport", transportName);
        editor.apply();
    }
}