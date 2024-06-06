package com.example.hello;

public class Hotel {
    private int id;
    private String name;
    private String location;
    private String imageUrl;

    public Hotel(int id, String name, String location, String imageUrl) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

