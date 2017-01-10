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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
