package com.theironyard.charlotte;

import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static ArrayList<Restaurant> restaurants = new ArrayList<>();

    public static void insertRestaurants(Connection conn, String name, String location, String foodType, double rating) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO restaurants VALUES (NULL, ?, ?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, location);
        stmt.setString(3, foodType);
        stmt.setDouble(4, rating);
        stmt.execute();
    }

    public static void deleteRestaurants(Connection conn, String name) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM restaurants WHERE name = ?");
        stmt.setString(1, name);
        stmt.execute();
    }

    public static ArrayList<Restaurant> selectRestaurants(Connection conn) throws SQLException {
        ArrayList<Restaurant> items = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM restaurants");
        while (results.next()) {
            String name = results.getString("name");
            String location = results.getString("location");
            String foodType = results.getString("foodType");
            double rating = results.getDouble("rating");
            items.add(new Restaurant(name, location, foodType, rating));
        }
        return items;
    }

//    public static void toggleToDo(Connection conn, int id) throws SQLException {
//        PreparedStatement stmt = conn.prepareStatement("UPDATE todos SET is_done = NOT is_done WHERE id = ?");
//        stmt.setInt(1, id);
//        stmt.execute();
//    }
//    public static void updateRestaurant(Connection conn, )


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
        stmt.execute("CREATE TABLE IF NOT EXISTS restaurants (id IDENTITY, name VARCHAR, location VARCHAR, foodType VARCHAR, rating DOUBLE)");
        System.out.println("Created");
        //ADDED THESE DURING LESSON
//        restaurants.add(new Restaurant(1, "Papa Johns", "Papa Johns Ave", "Pizza", 85.94));
//        restaurants.add(new Restaurant(2, "Wendys", "Wendy Street", "Fast food", 76.99));
//        restaurants.add(new Restaurant(3, "Cookout", "N Tryon Rd.", "DANK", 100.00));

        Spark.get("/", (req, res) -> {
            HashMap m = new HashMap();
            ArrayList<Restaurant> test = selectRestaurants(conn);

            m.put("restaurants", test);


            return new ModelAndView(m, "restaurant.html");
        }, new MustacheTemplateEngine());

        Spark.post("/create-restaurant", (req, res) -> {
            insertRestaurants(conn, req.queryParams("name"),
                                    req.queryParams("location"),
                                    req.queryParams("foodType"),
                                    Double.parseDouble(req.queryParams("rating")));
            res.redirect("/");
            return new ModelAndView(selectRestaurants(conn),"restaurant.html" );
        }, new MustacheTemplateEngine());

        Spark.post("/delete-restaurant", (req, res) -> {
            deleteRestaurants(conn, req.queryParams("name"));
            res.redirect("/");
            return new ModelAndView(selectRestaurants(conn),"restaurant.html" );
        }, new MustacheTemplateEngine());

    }
}
