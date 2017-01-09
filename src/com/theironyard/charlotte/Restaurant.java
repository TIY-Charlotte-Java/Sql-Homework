package com.theironyard.charlotte;

/**
 * Created by temp on 1/9/17.
 */
public class Restaurant {
    String name;
    String location;
    String foodType;
    boolean isOpen;
    double rating;

    public Restaurant(String name, String location, String foodType, boolean isOpen, double rating) {
        this.name = name;
        this.location = location;
        this.foodType = foodType;
        this.isOpen = isOpen;
        this.rating = rating;
    }
}
