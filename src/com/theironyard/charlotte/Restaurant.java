package com.theironyard.charlotte;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by mfahrner on 8/29/16.
 */
public class Restaurant {
    int id;
    String name;
    String type;
    int rating;

    public Restaurant(int id, String name, String type, int rating) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.rating = rating;
    }

    public static void insertRestaurant(Connection conn, String name, String type, int rating) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO restaurants VALUES (NULL, ?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, type);
        stmt.setInt(3, rating);
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
            String type = results.getString("type");
            int rating = results.getInt("rating");
            restaurants.add(new Restaurant(id, name, type, rating));
        }
        return restaurants;
    }

    public static void updateRestaurant(Connection conn, String name, String type, int rating, int id)
            throws SQLException {

        PreparedStatement stmt = conn.prepareStatement("UPDATE restaurants SET name = ?," +
                " type = ?, rating = ? WHERE id = ?");
        stmt.setString(1, name);
        stmt.setString(2, type);
        stmt.setInt(3, rating);
        stmt.setInt(4, id);
        stmt.execute();
    }

}


