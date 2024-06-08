package com.example.hello;

public class Restaurant {
    private int id;
    private String name;
    private String location;
    private double rating;
    private String imageUrl;

    public Restaurant(int id ,String name, String location, double rating, String imageUrl ) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.imageUrl = imageUrl;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
