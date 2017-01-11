package com.theironyard.charlotte;

import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;


import java.sql.*;
import java.util.HashMap;

public class Main {


    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS restaurants (id IDENTITY, name VARCHAR, type VARCHAR, location VARCHAR)");

        Spark.get(
                "/",

                (request, response) -> {

                    HashMap m = new HashMap();

                    m.put("restaurants", Restaurant.selectRestaurant(conn));
                    return new ModelAndView(m, "restaurants.html");
                },
                new MustacheTemplateEngine());

        Spark.post(
                "/create-restaurant",
                (request, response) -> {

                    String name = request.queryParams("name");
                    String type = request.queryParams("type");
                    String location = request.queryParams("location");
                    Restaurant.insertRestaurant(conn, name, type, location);

                    response.redirect("/");

                    return "";
                });

        Spark.post(
                "/delete-restaurant", (request, response) -> {

                    Restaurant.deleteRestaurant(conn, Integer.valueOf(request.queryParams("id")));
                    response.redirect("/");

                    return "";
                });

        Spark.post("/restaurants/:id", (request, response) -> {
            int id = Integer.valueOf(request.params(("id")));
            Restaurant current = Restaurant.getRestaurantById(conn, id);

            current.setName(request.queryParams("name"));
            current.setType(request.queryParams("type"));
            current.setLocation(request.queryParams("location"));

            Restaurant.updateRestaurant(conn, current);
            response.redirect("/");
            return "";
        });

        Spark.get("/restaurants/:id", (request, response) -> {
            HashMap m = new HashMap();

            int id = Integer.valueOf(request.params("id"));
            m.put("restaurants", Restaurant.getRestaurantById(conn, id));

            return new ModelAndView(m, "restaurant.html");
        }, new MustacheTemplateEngine());
    }
}
