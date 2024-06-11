package com.example.hello;

import java.util.List;

public class City {
    private String imageResourcePath;
    private String cityName;
    private List<LandMark> places;

    public City(String imageResourcePath, String cityName, List<LandMark> places) {
        this.imageResourcePath = imageResourcePath;
        this.cityName = cityName;
        this.places = places;
    }

    public String getImageResourcePath() {
        return imageResourcePath;
    }

    public String getCityName() {
        return cityName;
    }

    public List<LandMark> getPlaces() {
        return places;
    }
}
