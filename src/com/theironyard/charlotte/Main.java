package com.theironyard.charlotte;

import org.h2.tools.Server;

import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void insertRestaurants(Connection conn, String name, String location, String foodType, double rating) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO restaurant VALUES (NULL, ?, ?, ?, true, ?)");
        stmt.setString(1, name);
        stmt.setString(2, location);
        stmt.setString(3, foodType);
        stmt.setDouble(5, rating);
        stmt.execute();
    }

    public static void deleteRestaurants(Connection conn, String name) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM restaurant WHERE name = ?");
        stmt.setString(1, name);
        stmt.execute();
    }

    public static ArrayList<Restaurant> selectRestaurants(Connection conn) throws SQLException {
        ArrayList<Restaurant> items = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM restaurant");
        while (results.next()) {
            String name = results.getString("name");
            String location = results.getString("location");
            String foodType = results.getString("foodType");
            boolean isOpen = results.getBoolean("is_open");
            double rating = results.getDouble("rating");
            items.add(new Restaurant(name, location, foodType, isOpen, rating));
        }
        return items;
    }


    public static void main(String[] args) throws SQLException {
        try {
            Server.createWebServer().start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("hello");
        // creates connection object to build statements
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");

        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS restaurants (id IDENTITY, name VARCHAR, foodType VARCHAR, is_open BOOLEAN, rating DOUBLE)");


    }
}
