package com.theironyard.charlotte;

import org.h2.command.Prepared;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by jenniferchang on 8/29/16.
 */
public class Restaurant {
    private int id;
    public String name;
    public String city;
    public String type;
    public int rating;

    public Restaurant(int id, String name, String city, String type, int rating) {

        this.id = id;
        this.name = name;
        this.city = city;
        this.type = type;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public static void insertRestaurant(Connection conn, String name, String city, String type, int rating) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO restaurants VALUES (NULL, ?, ?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, city);
        stmt.setString(3, type);
        stmt.setInt(4, rating);
        stmt.execute();
        // static method returns nothing, accepts connection and restaurant attributes.
        // inserting a new restaurant into restaurants table
    }

    public static void deleteRestaurant(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM restaurants WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
        // delete restaurant from restaurants table where id is the input number
    }

    public static ArrayList<Restaurant> selectRestaurant(Connection conn) throws SQLException {
        ArrayList<Restaurant> restaurantsList = new ArrayList<>();
        Statement stmt = conn.createStatement(); // create a statement and execute sql query
        ResultSet results = stmt.executeQuery("SELECT * FROM restaurants");
        while (results.next()) { // while we have restaurants to process, get column values
            int id = results.getInt("id");
            String name = results.getString("name");
            String city = results.getString("city");
            String type = results.getString("type");
            int rating = results.getInt("rating");
            restaurantsList.add(new Restaurant(id, name, city, type, rating));
        }
        return restaurantsList;
        // this is put my results of select * from restaurants into an arraylist
    }

    public static void updateRestaurant(Connection conn, String name, String city, String type, int rating, int id)
            throws SQLException {
        PreparedStatement stmt =
                conn.prepareStatement("UPDATE restaurants SET name = ? , city = ?, type = ?, rating = ? WHERE id = ?");
        stmt.setString(1, name);
        stmt.setString(2, city);
        stmt.setString(3, type);
        stmt.setInt(4, rating);
        stmt.setInt(5, id);
        stmt.execute();
        //update restaurant based on id from the input
    }

    public static Restaurant specificRestaurant(Connection conn, int id) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM restaurants WHERE id = ?"); // first prepare statement
        preparedStatement.setObject(1, id); // set the id
        ResultSet results = preparedStatement.executeQuery(); // execute the prepared statement I created
        Restaurant restaurantObject = null; // create empty object
        while (results.next()) {
            String name = results.getString("name");
            String city = results.getString("city");
            String type = results.getString("type");
            int rating = results.getInt("rating");
            restaurantObject = new Restaurant(id, name, city, type, rating); // put the restaurant with that id into the new restaurant object
        }
        return restaurantObject;
    }

    public static ArrayList<Restaurant> searchRestaurant(Connection conn, String nameSearch) throws SQLException {
        PreparedStatement preparedStatement =
                conn.prepareStatement("SELECT * FROM restaurants WHERE LOWER(name) LIKE '%?%'");
        preparedStatement.setObject(1, nameSearch);
        ResultSet results = preparedStatement.executeQuery();
        ArrayList<Restaurant> restaurantsListSearch = new ArrayList<>();
        while (results.next()) {
            int id = results.getInt("id");
            String name = results.getString("name");
            String city = results.getString("city");
            String type = results.getString("type");
            int rating = results.getInt("rating");
            restaurantsListSearch.add(new Restaurant(id, name, city, type, rating));
        }
        return restaurantsListSearch;
    }

}




