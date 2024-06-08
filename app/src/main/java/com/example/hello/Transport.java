package com.example.hello;

public class Transport {

    private String type;
    private float costPerKM;

    public Transport(String type, float costPerKM) {
        this.type = type;
        this.costPerKM = costPerKM;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getCostPerKM() {
        return costPerKM;
    }

    public void setCostPerKM(float costPerKM) {
        this.costPerKM = costPerKM;
    }

}
