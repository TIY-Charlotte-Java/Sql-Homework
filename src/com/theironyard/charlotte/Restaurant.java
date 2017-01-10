package com.theironyard.charlotte;

/**
 * Created by emileenmarianayagam on 1/9/17.
 */
public class Restaurant {
    String name;
    String cuisine;
    String location;
    int rating;

    public Restaurant(){

    }

    public Restaurant(String name, String cuisine, String location,int rating) {
        this.name = name;
        this.cuisine = cuisine;
        this.location = location;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
