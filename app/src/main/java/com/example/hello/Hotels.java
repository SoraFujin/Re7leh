package com.example.hello;

public class Hotels {

    private int id;
    private String name;
    private String locaion;
    private float rating;
    private String imageName;

    public Hotels(int id, String name, String locaion, float rating, String imageName) {
        this.id = id;
        this.name = name;
        this.locaion = locaion;
        this.rating = rating;
        this.imageName = imageName;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocaion() {
        return locaion;
    }

    public void setLocaion(String locaion) {
        this.locaion = locaion;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return "ID = " + id + " name = " + name + " location = " + locaion + " rating = " + rating;
    }

}
