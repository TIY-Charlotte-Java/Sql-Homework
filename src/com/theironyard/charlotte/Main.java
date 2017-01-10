package com.theironyard.charlotte;

import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void insertRestaurant(Connection conn, String name, String cusine,String location,int rating) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO restaurant VALUES (NULL, ?, ?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2,cusine);
        stmt.setString(3,location);
        stmt.setInt(4,rating);
        stmt.execute();
    }



    public static ArrayList<Restaurant> selectRestaurant(Connection conn) throws SQLException {
        ArrayList<Restaurant> items = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM restaurant");
        while (results.next()) {
            String name = results.getString("name");
            String cuisine = results.getString("cuisine");
            String location = results.getString("location");
            int rating = results.getInt("rating");
            items.add(new Restaurant(name,cuisine,location,rating));
        }
        return items;
    }

    public static void deleteRestaurant(Connection conn, String name) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM restaurant WHERE NAME = ? ");
        stmt.setString(1, name);
        stmt.execute();

    }

    //private static ArrayList<Restaurant> restaurants = new ArrayList<>();

    public static void main(String[] args) throws SQLException{


        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();
        //if not exitsts
        stmt.execute("CREATE TABLE IF NOT EXISTS restaurant (id IDENTITY, name VARCHAR, cuisine VARCHAR, location VARCHAR,rating int)");

        ArrayList<Restaurant> restaurants = selectRestaurant(conn);

        restaurants.add(new Restaurant("pizza hut","american","20 mosley,",4));
        restaurants.add(new Restaurant("singapore gardens","chines","225 queen street",3));
        restaurants.add(new Restaurant("chicken coop", "souther","225 tryon",5));

        //getting the restaurant info
        // "/" entered go to the restatures
        Spark.get("/restaurants", (req, res) -> {
            HashMap m = new HashMap();

            m.put("restaurants", restaurants);

            return new ModelAndView(m,"restaurants.html");

        }, new MustacheTemplateEngine());


        Spark.post("/create-restaurant", (req, res) -> {
            //helps to insert the entered website to the table
            insertRestaurant(conn,
                            req.queryParams("name"),
                            req.queryParams("cuisine"),
                            req.queryParams("location"),
                            Integer.valueOf(req.queryParams("rating")));

            res.redirect("/restaurants");
            return new ModelAndView(conn,"restaurants.html");
            //return new ModelAndView(selectRestaurant(conn), "restaurants.html");


        },new MustacheTemplateEngine());





      /*  Spark.post("/create-restaurant", (req, res) -> {
            restaurants.add(
                    new Restaurant(
                            req.queryParams("name"),
                            req.queryParams("cuisine"),
                            req.queryParams("location"),
                            Integer.valueOf(req.queryParams("rating"))));

            res.redirect("/");
            return "";

        });*/







    }



}
