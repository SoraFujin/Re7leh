package com.example.hello;

import java.util.List;

public class City {
    private int imageResourceId;
    private String cityName;
    private List<LandMark> places;

    public City(int imageResourceId, String cityName, List<LandMark> places) {
        this.imageResourceId = imageResourceId;
        this.cityName = cityName;
        this.places = places;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getCityName() {
        return cityName;
    }

    public List<LandMark> getPlaces() {
        return places;
    }
}

