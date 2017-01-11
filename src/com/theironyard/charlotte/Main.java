package com.theironyard.charlotte;

import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    //insert method which creates connection -- name,cuisine,location and rating
    //we do not need a id as the values are injected an id will be created in the table
    public static void insertRestaurant(Connection conn, String name, String cuisine,String location,int rating) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO restaurants VALUES (NULL, ?, ?, ?, ? )");
        stmt.setString(1, name);
        stmt.setString(2,cuisine);
        stmt.setString(3,location);
        stmt.setInt(4,rating);
        stmt.execute();
    }


    public static ArrayList<Restaurant> selectRestaurant(Connection conn) throws SQLException {
        ArrayList<Restaurant> items = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM restaurants");
        while (results.next()) {
            int id = results.getInt("id");
            String name = results.getString("name");
            String cuisine = results.getString("cuisine");
            String location = results.getString("location");
            int rating = results.getInt("rating");
            items.add(new Restaurant(id, name,cuisine,location,rating));
        }
        return items;
    }

    public static void deleteRestaurant(Connection conn, int id) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM restaurants WHERE id = ? ");
        stmt.setInt(1, id);
        stmt.execute();
    }


    public static void updateRestaurant(Connection conn, Restaurant r) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("UPDATE restaurants SET name = ?, cuisine =?,  location =?, rating =? WHERE ID = ? ");
        stmt.setString(1, r.getName());
        stmt.setString(2, r.getCuisine());
        stmt.setString(3, r.getLocation());
        stmt.setInt(4, r.getRating());
        stmt.setInt(5, r.getId());
        stmt.execute();
    }

    public static Restaurant getRestaurantById (Connection conn, int id) throws SQLException {
        Restaurant item = null;
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM restaurants where id = ?");
        stmt.setInt(1,id);

        stmt.execute();
        ResultSet results = stmt.executeQuery();

        if(results.next()){
            String name  = results.getString("name");
            String cuisine = results.getString("cuisine");
            String location = results.getString("location");
            int rating = results.getInt("rating");
            item = new Restaurant(id,name,cuisine,location,rating);
        }
        return item;
    }


    public static void main(String[] args) throws SQLException{
        //HashMap m = new HashMap();

        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();
        //if not exitsts
        stmt.execute("CREATE TABLE IF NOT EXISTS restaurants (id IDENTITY, name VARCHAR, cuisine VARCHAR, location VARCHAR,rating int)");


        //getting the restaurant info
        // "/" the name for the url where you will get the info

        Spark.get("/", (req, res) -> {
            HashMap m = new HashMap();

            m.put("restaurants", selectRestaurant(conn));

            return new ModelAndView(m,"restaurants.html");

        }, new MustacheTemplateEngine());


        Spark.post("/create-restaurant", (req, res) -> {
            //helps to insert the entered website to the table
            insertRestaurant(conn,
                            req.queryParams("name"),
                            req.queryParams("cuisine"),
                            req.queryParams("location"),
                            Integer.valueOf(req.queryParams("rating")));

            res.redirect("/");
            return "";
        });


        Spark.post("/restaurants/:id", (req, res) ->{
            //when you post do not render a model and view
            int id = Integer.valueOf(req.params("id"));

            Restaurant current = getRestaurantById(conn,id);

            current.setName(req.queryParams("name"));
            current.setCuisine(req.queryParams("cuisine"));
            current.setLocation(req.queryParams("location"));
            current.setRating(Integer.valueOf(req.queryParams("rating")));

            updateRestaurant(conn,current);
            res.redirect("/");
            return "";
        } );

        Spark.get("/restaurants/:id",(request, response) -> {
            HashMap m = new HashMap();

            int id = Integer.valueOf(request.params("id"));

            m.put("restaurant",getRestaurantById(conn,id));
            //this is what you are returning
            return new ModelAndView(m,"restaurant.html");

        }, new MustacheTemplateEngine());

        Spark.post("/delete-restaurant", (req, res) -> {
            deleteRestaurant(conn,Integer.valueOf(req.queryParams("id")));
            res.redirect("/");
            return "";
        });
    }
}
