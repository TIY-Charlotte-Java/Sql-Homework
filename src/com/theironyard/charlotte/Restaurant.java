package com.theironyard.charlotte;

/**
 * Created by temp on 1/9/17.
 */
public class Restaurant {
    int id;
    String name;
    String location;
    String foodType;
    double rating;

    public Restaurant(int id, String name, String location, String foodType, double rating) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.foodType = foodType;
        this.rating = rating;
    }

    public Restaurant(String name, String location, String foodType, double rating) {
        this.name = name;
        this.location = location;
        this.foodType = foodType;
        this.rating = rating;
    }
}
