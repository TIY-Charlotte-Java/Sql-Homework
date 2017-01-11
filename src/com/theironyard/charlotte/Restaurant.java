package com.theironyard.charlotte;

import java.sql.*;
import java.util.ArrayList;


public class Restaurant {
    int id;
    String name;
    String type;
    String location;

    public Restaurant() {
    }

    public Restaurant(String name, String type, String location) {
        this.name = name;
        this.type = type;
        this.location = location;
    }

    public Restaurant(int id, String name, String type, String location) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.location = location;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static void insertRestaurant(Connection conn, String name, String type, String location) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO restaurants VALUES (NULL, ?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2,type);
        stmt.setString(3, location);
        stmt.execute();
    }

    public static ArrayList<Restaurant> selectRestaurant(Connection conn) throws SQLException {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM restaurants");
        while (results.next()) {
            int id = results.getInt("id");
            String name = results.getString("name");
            String type = results.getString("type");
            String location = results.getString("location");
            restaurants.add(new Restaurant(id, name, type, location));
        }
        return restaurants;
    }

    public static void deleteRestaurant(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM restaurants WHERE id = ? ");
        stmt.setInt(1, id);
        stmt.execute();
    }

    public static void updateRestaurant(Connection conn, Restaurant r) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE restaurants SET name = ?, type = ?, location = ? WHERE id = ?");
        stmt.setString(1, r.getName());
        stmt.setString(2, r.getType());
        stmt.setString(3, r.getLocation());
        stmt.setInt(4, r.getId());
        stmt.execute();
    }

    public static Restaurant getRestaurantById(Connection conn, int id) throws SQLException {
        Restaurant ID = null;
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM restaurants WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
        ResultSet results = stmt.executeQuery();

        if (results.next()) {
            String name = results.getString("name");
            String type = results.getString("type");
            String location = results.getString("location");
            ID = new Restaurant(id, name, type, location);
        }
        return ID;
    }
}

