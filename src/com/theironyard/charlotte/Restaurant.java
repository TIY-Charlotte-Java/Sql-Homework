package com.theironyard.charlotte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by mfahrner on 8/29/16.
 */
public class Restaurant {
    String name;
    String type;
    int rating;

    public Restaurant(String name, String type, int rating) {
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
}


