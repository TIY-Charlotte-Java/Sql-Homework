package com.theironyard.charlotte;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import static org.junit.Assert.*;

/**
 * Created by jenniferchang on 8/30/16.
 */
public class RestaurantTest {

    public Connection startConnection() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS restaurants (id IDENTITY, name VARCHAR, city VARCHAR, type VARCHAR, rating INT)"); // create restaurants database
        return conn;
    }


//    public void testRestaurant() throws SQLException {
//        Connection conn = startConnection();
//        Restaurant.insertRestaurant(conn, "name", "city", "type", 1);
//        Restaurant restaurant = Restaurant.selectRestaurant(conn);
//        Main.insertMessage(conn, user.id, -1, "Hello, world!");
//        Message message = Main.selectMessage(conn, 1);
//        conn.close();
//        assertTrue(message != null);
//    }


    @Test
    public void testSearch() throws SQLException {
        Connection conn = startConnection();
        Restaurant.insertRestaurant(conn,"Jimmy Johns", "city", "type", 4);
        ArrayList<Restaurant> searchResults = Restaurant.searchRestaurant(conn, "Jimmy");
        conn.close();
        assertTrue(searchResults.size() == 1);

    }
}