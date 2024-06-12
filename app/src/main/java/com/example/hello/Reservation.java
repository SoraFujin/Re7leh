package com.example.hello;

public class Reservation {
    private int id;
    private int user_id;
    private int city_id;
    private int place_id;
    private int hotel_id;
    private int restaurant_id;
    private int cars_id;
    private String city_name;
    private String place_name;
    private String hotel_name;
    private String restaurant_name;
    private String car_name;

    // Getters
    public int getId() { return id; }
    public int getUserId() { return user_id; }
    public int getCityId() { return city_id; }
    public int getPlaceId() { return place_id; }
    public int getHotelId() { return hotel_id; }
    public int getRestaurantId() { return restaurant_id; }
    public int getCarsId() { return cars_id; }
    public String getCityName() { return city_name; }
    public String getPlaceName() { return place_name; }
    public String getHotelName() { return hotel_name; }
    public String getRestaurantName() { return restaurant_name; }
    public String getCarName() { return car_name; }
}
