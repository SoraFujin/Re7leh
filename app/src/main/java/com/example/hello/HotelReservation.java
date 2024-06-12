package com.example.hello;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class HotelReservation extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private List<Room> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotelreservation);

        int hotelId = getIntent().getIntExtra("hotelId", -1);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        roomList = new ArrayList<>();
        roomAdapter = new RoomAdapter(roomList, this);
        recyclerView.setAdapter(roomAdapter);

        fetchRooms(hotelId);
    }

    private void fetchRooms(int hotelId) {
        String url = "http://10.0.2.2/travelapp/get_rooms.php?hotel_id=" + hotelId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        roomList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String type = jsonObject.getString("type");
                                String description = jsonObject.getString("description");
                                double price = jsonObject.getDouble("price");
                                String imageUrl = jsonObject.getString("image_url");

                                Room room = new Room(id, type, description, price, imageUrl);
                                roomList.add(room);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        roomAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}