package com.example.hello;

public class PopularPlace {
    private String name;
    private int imageResourceId;

    public PopularPlace(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
