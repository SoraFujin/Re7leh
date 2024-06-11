package com.example.hello;

public class LandMark {
    private String name;
    private String imageUrl;
    private String cityName;
    private int placeID;


    public String getCityName() {
        return cityName;
    }

    public LandMark(String imageUrl, String name, int placeID){
        this.imageUrl = imageUrl;
        this.name = name;
        this.placeID = placeID;
    }
    public LandMark(String imageUrl, String name, String cityName){
        this.imageUrl = imageUrl;
        this.name = name;
        this.cityName = cityName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName(){
        return name;
    }

    public int getPlaceID() {
        return placeID;
    }

}
