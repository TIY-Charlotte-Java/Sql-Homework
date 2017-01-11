package com.theironyard.charlotte;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by kelseynewman on 1/9/17.
 */
public class Restaurant {
    int id;
    String name;
    String location;
    String cuisine;
    int rating;

    public Restaurant(){

    }

    public Restaurant(int id, String name, String location, String cuisine, int rating) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.cuisine = cuisine;
        this.rating = rating;
    }

    public Restaurant(String name, String location, String cuisine, int rating) {
        this.name = name;
        this.location = location;
        this.cuisine = cuisine;
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

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public static void insertRestaurant(Connection conn, Restaurant r) throws SQLException {
        //build restaurant object and pass that in
        //instead of String name, String location, String cuisine, int rating
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO restaurants VALUES (NULL, ?, ?, ?, ?)");
        stmt.setString(1, r.getName());
        stmt.setString(2, r.getLocation());
        stmt.setString(3, r.getCuisine());
        stmt.setInt(4, r.getRating());
        stmt.execute();
    }

    public static void deleteRestaurant(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM restaurants WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }

    public static ArrayList<Restaurant> selectRestaurants(Connection conn) throws SQLException {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM restaurants");
        while (results.next()) {
            int id = results.getInt("id");
            String name = results.getString("name");
            String location = results.getString("location");
            String cuisine = results.getString("cuisine");
            int rating = results.getInt("rating");

            restaurants.add(new Restaurant(id, name, location, cuisine, rating));
        }
        return restaurants;
    }

    public static Restaurant getRestaurantById (Connection conn, int id) throws SQLException {
        Restaurant restaurant = null;
        PreparedStatement stmt = conn.prepareStatement("select * from restaurants where id = ?");
        stmt.setInt(1, id);

        ResultSet results = stmt.executeQuery();

        if (results.next()) {
            String name = results.getString("name");
            String location = results.getString("location");
            String cuisine = results.getString("cuisine");
            int rating = results.getInt("rating");
            restaurant = new Restaurant(id, name, location, cuisine, rating);
        }
        return restaurant;
    }

    public static void updateRestaurant (Connection conn, Restaurant r ) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("update restaurants set name = ?, location = ?, cuisine = ?, rating = ? where id = ?");
        stmt.setString(1, r.getName());
        stmt.setString(2, r.getLocation());
        stmt.setString(3, r.getCuisine());
        stmt.setInt(4, r.getRating());
        stmt.setInt(5, r.getId());
        stmt.execute();
    }

    public static ArrayList<Restaurant> searchRestaurants (Connection conn, String searchName) throws SQLException {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("select * from restaurants where upper(name) LIKE ?");
        stmt.setString(1, "%" + searchName.toUpperCase() + "%");
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
            int id = results.getInt("id");
            String name = results.getString("name");
            String location = results.getString("location");
            String cuisine = results.getString("cuisine");
            int rating = results.getInt("rating");

            restaurants.add(new Restaurant(id, name, location, cuisine, rating));
        }
        return restaurants;
    }
}
