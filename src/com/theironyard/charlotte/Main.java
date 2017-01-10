package com.theironyard.charlotte;

import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    //private static ArrayList<Restaurant> restaurants = new ArrayList<>();
    // This inserts the new restaurant into he database
    public static void insertRestaurant(Connection conn, String name, String location, String foodType, double rating) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO restaurants VALUES (NULL, ?, ?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, location);
        stmt.setString(3, foodType);
        stmt.setDouble(4, rating);
        stmt.execute();
    }
    // this deletes the restaurant from the database by the name query
    public static void deleteRestaurants(Connection conn, String name) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM restaurants WHERE name = ?");
        stmt.setString(1, name);
        stmt.execute();
    }
    // this lets your manipulate the restaurant
    public static void updateRestaurants(Connection conn, Restaurant r) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE restaurants SET name = ? ,location = ?, foodType = ? ,rating = ? WHERE ID = ?");
        stmt.setString(1, r.getName());
        stmt.setString(2, r.getLocation());
        stmt.setString(3, r.getFoodType());
        stmt.setDouble(4, r.getRating());
        stmt.setInt(5,r.getId());
        stmt.execute();

    }
    // this shows the list of restaurants stored in the database
    public static ArrayList<Restaurant> selectRestaurants(Connection conn) throws SQLException {
        ArrayList<Restaurant> items = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM restaurants");
        while (results.next()) {
            int id = results.getInt("id");
            String name = results.getString("name");
            String location = results.getString("location");
            String foodType = results.getString("foodType");
            double rating = results.getDouble("rating");
            items.add(new Restaurant(id, name, location, foodType, rating));
        }
        return items;
    }
    // this finds a specific restaurant by it's id
    public static Restaurant getRestaurantById (Connection conn, int id) throws SQLException {
        Restaurant item = null;
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM restaurants where id = ?");
        stmt.setInt(1, id);
        stmt.execute();

        ResultSet result = stmt.executeQuery();
        if(result.next()) {
            String name = result.getString("name");
            String location = result.getString("location");
            String foodType = result.getString("foodType");
            double rating = result.getDouble("rating");
            item = new Restaurant(id, name, location, foodType, rating);
        }
        return item;
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
        // this sql statement creates a table called restaurants if non already exists
        stmt.execute("CREATE TABLE IF NOT EXISTS restaurants (id IDENTITY, name VARCHAR, location VARCHAR, foodType VARCHAR, rating DOUBLE)");
        System.out.println("Created");
        //ADDED THESE DURING LESSON
//        restaurants.add(new Restaurant(1, "Papa Johns", "Papa Johns Ave", "Pizza", 85.94));
//        restaurants.add(new Restaurant(2, "Wendys", "Wendy Street", "Fast food", 76.99));
//        restaurants.add(new Restaurant(3, "Cookout", "N Tryon Rd.", "DANK", 100.00));
        // Home page with list of restaurants that are stored

        Spark.get("/", (req, res) -> {
            HashMap m = new HashMap();
            //ArrayList<Restaurant> test = selectRestaurants(conn);
            m.put("restaurants", selectRestaurants(conn));

            return new ModelAndView(m, "restaurant.html");
        }, new MustacheTemplateEngine());

        // Lets you create a restaurant
        Spark.post("/create-restaurant", (req, res) -> {
            insertRestaurant(conn, req.queryParams("name"),
                                    req.queryParams("location"),
                                    req.queryParams("foodType"),
                                    Double.parseDouble(req.queryParams("rating")));
            res.redirect("/");
            return"";
        });

        // lets your delete a restaurant
        Spark.post("/delete-restaurant", (req, res) -> {
            deleteRestaurants(conn, req.queryParams("name"));
            res.redirect("/");
            return new ModelAndView(selectRestaurants(conn),"restaurant.html" );
        }, new MustacheTemplateEngine());

        // allows you to update restaurant that is currently been selected
        Spark.post("/restaurants/:id", (req, res) -> {
            int id = Integer.valueOf(req.params("id"));

            Restaurant current = getRestaurantById(conn, id);
            //current.setId(Integer.valueOf(req.queryParams("id")));
            current.setName(req.queryParams("name"));
            current.setLocation(req.queryParams("location"));
            current.setFoodType(req.queryParams("foodType"));
            current.setRating(Double.valueOf(req.queryParams("rating")));

            updateRestaurants(conn, current);
            res.redirect("/");
            return "";
        });

        // shows you the selected restaurant that is to be edited
        Spark.get("/restaurants/:id", (req, res) -> {
            int id = Integer.valueOf(req.params("id"));
            Restaurant current = getRestaurantById(conn, id);
            HashMap s = new HashMap();
            s.put("restaurant", current);

            return new ModelAndView(s, "changeRestaurant.html");
        }, new MustacheTemplateEngine());
    }
}
