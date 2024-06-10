package com.example.hello;

public class LandMark {
    private String name;
    private String imageUrl;
private String cityName;

    public String getCityName() {
        return cityName;
    }

    public LandMark(String imageUrl, String name){
        this.imageUrl = imageUrl;
        this.name = name;
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
}
