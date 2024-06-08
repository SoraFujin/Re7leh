package com.example.hello;

public class Tours {
    private int id;
    private String tourName;
    private String destination;
    private int usersId;
    private int hotelsId;
    private int restaurantsId;
    private String transportId;

    // Constructor
    public Tours(int id, String tourName, String destination, int usersId, int hotelsId, int restaurantsId, String transportId) {
        this.id = id;
        this.tourName = tourName;
        this.destination = destination;
        this.usersId = usersId;
        this.hotelsId = hotelsId;
        this.restaurantsId = restaurantsId;
        this.transportId = transportId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getUsersId() {
        return usersId;
    }

    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    public int getHotelsId() {
        return hotelsId;
    }

    public void setHotelsId(int hotelsId) {
        this.hotelsId = hotelsId;
    }

    public int getRestaurantsId() {
        return restaurantsId;
    }

    public void setRestaurantsId(int restaurantsId) {
        this.restaurantsId = restaurantsId;
    }

    public String getTransportId() {
        return transportId;
    }

    public void setTransportId(String transportId) {
        this.transportId = transportId;
    }
}
