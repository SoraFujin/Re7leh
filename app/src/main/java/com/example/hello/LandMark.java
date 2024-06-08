package com.example.hello;

public class LandMark {
    private String name;
    private int imageResourceId;

    public LandMark(int imageResourceId, String name){
        this.imageResourceId = imageResourceId;
        this.name = name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getName(){
        return name;
    }
}
