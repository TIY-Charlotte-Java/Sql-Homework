package com.theironyard.charlotte;

import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    //private static ArrayList<Restaurant> restaurants = new ArrayList<>();
    static HashMap searchResults = new HashMap();

    public static void main(String[] args) throws SQLException {

        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS restaurants (id IDENTITY, name VARCHAR, address VARCHAR, type VARCHAR, price VARCHAR, rating INT)");

        Spark.post("/restaurants",(req, res) -> {
            res.redirect("/restaurants");
            return "";
        });

        Spark.get("/restaurants", (req, res) -> {
            HashMap m = new HashMap();

            m.put("restaurants", Restaurant.selectRestaurants(conn));
            return new ModelAndView(m, "restaurants.html");
        }, new MustacheTemplateEngine());

        Spark.post("/create-restaurant", (req, res) -> {
            Restaurant current = new Restaurant(
                    req.queryParams("name"),
                    req.queryParams("address"),
                    req.queryParams("type"),
                    req.queryParams("price"),
                    Integer.valueOf(req.queryParams("rating")));
            Restaurant.insertRestaurant(conn, current);
            res.redirect("/restaurants");
            return "";
        });

        Spark.get("/restaurant/:id", (req, res) -> {
            int id = Integer.valueOf(req.params("id"));

            HashMap m = new HashMap();

            m.put("restaurant", Restaurant.selectRestaurantById(conn, id));

            return new ModelAndView(m, "restaurant.html");
        }, new MustacheTemplateEngine());


        Spark.post("/restaurant/:id", (req, res) -> {
            int id = Integer.valueOf(req.params("id"));
            Restaurant current = Restaurant.selectRestaurantById(conn, id);
            current.setAddress(req.queryParams("address"));
            current.setName(req.queryParams("name"));
            current.setPrice(req.queryParams("price"));
            current.setType(req.queryParams("type"));
            current.setRating(Integer.valueOf(req.queryParams("rating")));

            Restaurant.updateRestaurant(conn, current);
            res.redirect("/restaurants");
            return "";
        });
        Spark.post("/delete-restaurant/:id", (req, res) -> {
            int id = Integer.valueOf(req.params("id"));

            Restaurant.deleteRestaurant(conn, id);
            res.redirect("/restaurants");
            return "";
        });
        Spark.get("/restaurant-search",(req,res) ->{
            return new ModelAndView(searchResults, "restaurant-search.html");
        }, new MustacheTemplateEngine());

        Spark.post("/restaurant-search",(req, res) ->{
            searchResults.put("restaurants", Restaurant.selectRestaurantsByName(conn, req.queryParams("name")));
            res.redirect("/restaurant-search");
            return "";
        });
    }
}

