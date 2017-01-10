package com.theironyard.charlotte;

import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.*;
import java.util.HashMap;

public class Main {
    //put SQL methods into Restaurant class

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS restaurants (id IDENTITY, name VARCHAR, location VARCHAR, cuisine VARCHAR, rating INT)");

        Spark.get("/", ((req, res) ->{
            HashMap model = new HashMap();

            model.put("restaurants", Restaurant.selectRestaurants(conn));

            return new ModelAndView(model, "home.html");
        }), new MustacheTemplateEngine());

        Spark.get("/create-restaurant", ((req, res) ->{
            HashMap model = new HashMap();

            model.put("restaurants", Restaurant.selectRestaurants(conn));

            return new ModelAndView(model, "restaurants.html");
        }), new MustacheTemplateEngine());

        Spark.post("/create-restaurant", (req, res) -> {
            //insertRestaurants method
            //add query params
            Restaurant newRestaurant = new Restaurant(
                    req.queryParams("name"),
                    req.queryParams("location"),
                    req.queryParams("cuisine"),
                    Integer.valueOf(req.queryParams("rating")));
            Restaurant.insertRestaurant(conn, newRestaurant);

            res.redirect("/");
            return "";
        });

        //need a get request for /restaurants/:id
        Spark.get("/restaurants/:id", ((req, res) -> {
            HashMap model = new HashMap();
            int id = Integer.valueOf(req.params("id"));

            model.put("restaurant", Restaurant.getRestaurantById(conn, id));

            return new ModelAndView(model, "restaurant.html");
        }), new MustacheTemplateEngine());

        //need a post request for /restaurants/:id to update that restaurant
        Spark.post("/restaurants/:id", (req, res) -> {
            int id = Integer.valueOf(req.params("id"));

            Restaurant current = Restaurant.getRestaurantById(conn, id);

            current.setName(req.queryParams("name"));
            current.setLocation(req.queryParams("location"));
            current.setCuisine(req.queryParams("cuisine"));
            current.setRating(Integer.valueOf(req.queryParams("rating")));

            Restaurant.updateRestaurant(conn, current);

            res.redirect("/");
            return "";
        });

        Spark.post("/delete-restaurant", (req, res) -> {
            Restaurant.deleteRestaurant(conn, Integer.valueOf(req.queryParams("id")));

            res.redirect("/");
            return "";
        });

    }
}
