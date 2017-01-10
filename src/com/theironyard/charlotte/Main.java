package com.theironyard.charlotte;

import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    //put SQL methods into Restaurant class

    public static void insertRestaurant(Connection conn, String name, String location, String cuisine, int rating) throws SQLException {
       //build restaurant object and pass that in
        //instead of String name, String location, String cuisine, int rating
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO restaurants VALUES (NULL, ?, ?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, location);
        stmt.setString(3, cuisine);
        stmt.setInt(4, rating);
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

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS restaurants (id IDENTITY, name VARCHAR, location VARCHAR, cuisine VARCHAR, rating INT)");

        Spark.get("/", ((req, res) ->{
            HashMap model = new HashMap();

            model.put("restaurants", selectRestaurants(conn));

            return new ModelAndView(model, "home.html");
        }), new MustacheTemplateEngine());

        Spark.get("/create-restaurant", ((req, res) ->{
            HashMap model = new HashMap();

            model.put("restaurants", selectRestaurants(conn));

            return new ModelAndView(model, "restaurants.html");
        }), new MustacheTemplateEngine());

        Spark.post("/create-restaurant", (req, res) -> {
            //insertRestaurants method
            //add query params
            insertRestaurant(conn,
                    req.queryParams("name"),
                    req.queryParams("location"),
                    req.queryParams("cuisine"),
                    Integer.valueOf(req.queryParams("rating")));

            res.redirect("/");
            return "";
        });

        //need a get request for /restaurants/:id
        Spark.get("/restaurants/:id", ((req, res) -> {
            HashMap model = new HashMap();
            int id = Integer.valueOf(req.params("id"));

            model.put("restaurant", getRestaurantById(conn, id));

            return new ModelAndView(model, "restaurant.html");
        }), new MustacheTemplateEngine());

        //need a post request for /restaurants/:id to update that restaurant
        Spark.post("/restaurants/:id", (req, res) -> {
            int id = Integer.valueOf(req.params("id"));

            Restaurant current = getRestaurantById(conn, id);

            current.setName(req.queryParams("name"));
            current.setLocation(req.queryParams("location"));
            current.setCuisine(req.queryParams("cuisine"));
            current.setRating(Integer.valueOf(req.queryParams("rating")));

            updateRestaurant(conn, current);

            res.redirect("/");
            return "";
        });

        Spark.post("/delete-restaurant", (req, res) -> {
            deleteRestaurant(conn, Integer.valueOf(req.queryParams("id")));

            res.redirect("/");
            return "";
        });
    }
}
