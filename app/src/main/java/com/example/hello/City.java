package com.example.hello;

import java.util.List;

public class City {
    private String imageResourcePath;
    private String cityName;
    private List<LandMark> places;

    private int cityID;



    public City(String imageResourcePath, String cityName, List<LandMark> places, int cityID) {
        this.imageResourcePath = imageResourcePath;
        this.cityName = cityName;
        this.places = places;
        this.cityID = cityID;
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


    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }
}
