package com.theironyard.charlotte;

/**
 * Created by emileenmarianayagam on 1/9/17.
 */
public class Restaurant {
    int id;
    String name;
    String cuisine;
    String location;
    int rating;

    public Restaurant(){

    }

    public Restaurant(String name, String cuisine, String location, int rating) {
        this.name = name;
        this.cuisine = cuisine;
        this.location = location;
        this.rating = rating;
    }

    public Restaurant(int id, String name, String cuisine, String location, int rating) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.location = location;
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
