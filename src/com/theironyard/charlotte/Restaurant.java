package com.theironyard.charlotte;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by graceconnelly on 1/9/17.
 */

public class Restaurant {
    int id;
    String name;
    String address;
    String type;
    String price;
    int rating;

    public Restaurant(int id, String name, String address, String type, String price, int rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.type = type;
        this.price = price;
        this.rating = rating;
    }

    public Restaurant(String name, String address, String type, String price, int rating) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.price = price;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    //populates a restaurant based on selectMethods.
    public static Restaurant populateRestaurant(ResultSet results) throws SQLException {
        int id = results.getInt("id");
        String name = results.getString("name");
        String address = results.getString("address");
        String type = results.getString("type");
        String price = results.getString("price");
        int rating = results.getInt("rating");
        return new Restaurant(id, name, address, type, price, rating);
    }
    //START Methods that get things from the database
    public static ArrayList<Restaurant> selectRestaurants(Connection conn) throws SQLException {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM restaurants");
        while (results.next()) {
            restaurants.add(populateRestaurant(results));
        }
        return restaurants;
    }
    public static Restaurant selectRestaurantById(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM restaurants where id = ?");
        stmt.setInt(1, id);
        ResultSet results = stmt.executeQuery();
        if(results.next()) {
            return populateRestaurant(results);
        }
        else {
            return null;
        }
    }
    public static ArrayList<Restaurant> selectRestaurantsByName(Connection conn, String searchName) throws SQLException{
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM restaurants where upper(name) = ?");
        stmt.setString(1, searchName.toUpperCase());
        ResultSet nameResults =  stmt.executeQuery();
        while (nameResults.next()){
            restaurants.add(populateRestaurant(nameResults));
        }
        return restaurants;
    }
    //START Methods that modify the database.
    public static void updateRestaurant(Connection conn, Restaurant  updateR) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE restaurants SET name = ?, address = ?, type = ?, price = ?, rating = ? WHERE ID = ?");
        stmt.setString(1, updateR.getName());
        stmt.setString(2,updateR.getAddress());
        stmt.setString(3,updateR.getType());
        stmt.setString(4, updateR.getPrice());
        stmt.setInt(5,updateR.getRating());
        stmt.setInt(6,updateR.getId());
        stmt.execute();
    }
    public static void insertRestaurant(Connection conn, Restaurant newR) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO restaurants VALUES (NULL, ?, ?, ?, ?, ?)");
        stmt.setString(1, newR.getName());
        stmt.setString(2, newR.getAddress());
        stmt.setString(3, newR.getType());
        stmt.setString(4, newR.getPrice());
        stmt.setInt(5,newR.getRating());
        stmt.execute();
    }
    public static void deleteRestaurant(Connection conn, int id) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM restaurants WHERE ID = ?");
        stmt.setInt(1,id);
        stmt.execute();
    }
}