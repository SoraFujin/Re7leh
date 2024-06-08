package com.example.hello;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomListAdapter<T> extends BaseAdapter {
    private Context context;
    private List<T> objects;
    private LayoutInflater inflater;

    public CustomListAdapter(Context context, List<T> objects) {
        this.context = context;
        this.objects = objects;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.layout = convertView.findViewById(R.id.dynamicLayout);
            holder.imageViewEdit = convertView.findViewById(R.id.icon_edit);
            holder.imageViewRemove = convertView.findViewById(R.id.icon_remove);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Clear the layout first
        holder.layout.removeAllViews();

        // Set object data to views dynamically
        T obj = objects.get(position);
        if (obj instanceof Hotels) {
            Hotels hotels = (Hotels) obj;
            addTextView(holder.layout, String.valueOf(hotels.getID()), "ID");
            addTextView(holder.layout, hotels.getName(), "Name");
            addTextView(holder.layout, hotels.getLocaion(), "Location");
            addTextView(holder.layout, String.valueOf(hotels.getRating()), "Rating");
            addTextView(holder.layout, hotels.getImageName(), "Image Name");
        } else if (obj instanceof Restaurants) {
            Restaurants restaurant = (Restaurants) obj;
            addTextView(holder.layout, String.valueOf(restaurant.getID()), "ID");
            addTextView(holder.layout, restaurant.getName(), "Name");
            addTextView(holder.layout, restaurant.getLocaion(), "Location");
            addTextView(holder.layout, String.valueOf(restaurant.getRating()), "Rating");
            addTextView(holder.layout, restaurant.getImageName(), "Image Name");
        } else if (obj instanceof Transport) {
            Transport transport = (Transport) obj;
            addTextView(holder.layout, transport.getType(), "Type");
            addTextView(holder.layout, String.valueOf(transport.getCostPerKM()), "Cost Per KM");
        }else if (obj instanceof Tours) {
            Tours tours = (Tours) obj;
            addTextView(holder.layout, String.valueOf(tours.getId()), "ID");
            addTextView(holder.layout, tours.getTourName(), "Tour Name");
            addTextView(holder.layout, tours.getDestination(), "Destination");
            addTextView(holder.layout, String.valueOf(tours.getUsersId()), "UserID");
            addTextView(holder.layout, String.valueOf(tours.getHotelsId()), "HotelID");
            addTextView(holder.layout, String.valueOf(tours.getRestaurantsId()),"RestaurantID");
            addTextView(holder.layout, tours.getTransportId(),"TransportID");
        }

        holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (obj instanceof Hotels) {
                    Hotels hotels = (Hotels) obj;
                    Intent intent = new Intent(context, AddEditHR.class);
                    intent.putExtra("type", 0);
                    intent.putExtra("op", 1);

                    intent.putExtra("id", hotels.getID());
                    intent.putExtra("name", hotels.getName());
                    intent.putExtra("location", hotels.getLocaion());
                    intent.putExtra("rating", hotels.getRating());
                    intent.putExtra("imageName", hotels.getImageName());
                    context.startActivity(intent);
                } else if (obj instanceof Restaurants) {
                    Restaurants restaurant = (Restaurants) obj;
                    Intent intent = new Intent(context, AddEditHR.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("op", 1);

                    intent.putExtra("id", restaurant.getID());
                    intent.putExtra("name", restaurant.getName());
                    intent.putExtra("location", restaurant.getLocaion());
                    intent.putExtra("rating", restaurant.getRating());
                    intent.putExtra("imageName", restaurant.getImageName());
                    context.startActivity(intent);
                } else if (obj instanceof Transport) {
                    Transport transport = (Transport) obj;
                    Intent intent = new Intent(context, AddEditTransport.class);
                    intent.putExtra("op", 1);

                    intent.putExtra("type", transport.getType());
                    intent.putExtra("cost", transport.getCostPerKM());
                    context.startActivity(intent);
                }else if (obj instanceof Tours) {
                    Tours tours = (Tours) obj;
                    Intent intent = new Intent(context, AddEditTour.class);
                    intent.putExtra("op", 1);

                    intent.putExtra("id", tours.getId());
                    intent.putExtra("tourname", tours.getTourName());
                    intent.putExtra("destination", tours.getDestination());
                    intent.putExtra("users_id", tours.getUsersId());
                    intent.putExtra("hotels_id", tours.getHotelsId());
                    intent.putExtra("restaurants_id", tours.getRestaurantsId());
                    intent.putExtra("transport_id", tours.getTransportId());
                    context.startActivity(intent);
                }
            }
        });

        holder.imageViewRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (obj instanceof Hotels) {
                    Hotels hotels = (Hotels) obj;
                    removeHotel(hotels.getID());
                    removeItem(position);
                } else if (obj instanceof Restaurants) {
                    Restaurants restaurant = (Restaurants) obj;
                    removeRestaurrants(restaurant.getID());
                    removeItem(position);
                } else if (obj instanceof Transport) {
                    Transport transport = (Transport) obj;
                    removeTransport(transport.getType());
                    removeItem(position);
                }else if (obj instanceof Tours) {
                    Tours tours = (Tours) obj;
                    removeTour(tours.getId());
                    removeItem(position);
                }
            }
        });

        return convertView;
    }

    private void addTextView(LinearLayout layout, String text, String columnName) {
        TextView textView = new TextView(context);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(20);
        text = columnName + ": " + text;
        textView.setText(text);
        layout.addView(textView);
    }

    public void removeItem(int position) {
        objects.remove(position);
        notifyDataSetChanged();
    }

    private void removeHotel(int id){
        String url = "http://10.0.2.2/android/remove_hotels.php";

        RequestQueue queue = Volley.newRequestQueue(context);


        // Create a JsonObjectRequest with POST method
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.

            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("id", String.valueOf(id));

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }

    private void removeRestaurrants(int id){
        String url = "http://10.0.2.2/android/remove_restaurants.php";

        RequestQueue queue = Volley.newRequestQueue(context);


        // Create a JsonObjectRequest with POST method
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.

            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("id", String.valueOf(id));

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }

    private void removeTransport(String type){
        String url = "http://10.0.2.2/android/remove_transport.php";

        RequestQueue queue = Volley.newRequestQueue(context);


        // Create a JsonObjectRequest with POST method
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.

            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("type", type);

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }

    private void removeTour(int id){
        String url = "http://10.0.2.2/android/remove_tours.php";

        RequestQueue queue = Volley.newRequestQueue(context);


        // Create a JsonObjectRequest with POST method
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.

            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("id", String.valueOf(id));

                // at last we are returning our params.
                return params;
            }
        };
        queue.add(request);
    }

    private static class ViewHolder {
        LinearLayout layout;
        ImageView imageViewAdd;
        ImageView imageViewEdit;
        ImageView imageViewRemove;
    }
}