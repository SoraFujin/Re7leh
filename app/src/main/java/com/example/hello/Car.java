package com.example.hello;

public class Car {
    private int id;
    private String type;
    private String description;
    private double price;
    private String imageUrl;

    public Car(int id, String type, String description, double price, String imageUrl) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}