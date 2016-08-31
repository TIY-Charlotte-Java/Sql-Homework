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
        stmt.execute("CREATE TABLE IF NOT EXISTS restaurants (id IDENTITY, name VARCHAR, type VARCHAR, rating INT)");

        Spark.init();

        Spark.get(

                "/",

                ((request, response) -> {

                    HashMap m = new HashMap<>();
                    m.put("restaurants", Restaurant.selectRestaurant(conn));

                    return new ModelAndView(m, "home.html");

                }),

                new MustacheTemplateEngine()
        );

        Spark.post(

                "/create-restaurant",

                (request, response) -> {

                    String name = request.queryParams("name");
                    String type = request.queryParams("type");
//                    int rating = 2;

                    int rating = Integer.valueOf(request.queryParams("rating"));

                    Restaurant.insertRestaurant(conn, name, type, rating);

                    response.redirect("/");
                    return "";
                }
        );

        Spark.post(

                "/delete-restaurant",
                ((request, response) -> {

                    int deleteID = Integer.valueOf(request.queryParams("deleteID"));

                    Restaurant.deleteRestaurant(conn, deleteID);

                    response.redirect("/");

                    return "";
                })
        );

        Spark.get("/edit/:id" ,

                ((request, response) -> {

                    int id = Integer.valueOf(request.params("id"));
                    String name = Restaurant.restaurantSelect(conn, id).name;
                    String type = Restaurant.restaurantSelect(conn, id).type;
                    int rating = Restaurant.restaurantSelect(conn, id).rating;

                    HashMap l = new HashMap<>();
                    l.put("id", id);
                    l.put("name", name);
                    l.put("type", type);
                    l.put("rating", rating);

                    return new ModelAndView(l, "edit.html");

                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/edit-restaurant/:id",
                ((request, response) -> {
                    int id = Integer.valueOf(request.params("id"));
                    String name = request.queryParams("name");
                    String type = request.queryParams("type");
                    int rating = Integer.valueOf(request.queryParams("rating"));

                    Restaurant.updateRestaurant(conn, id, name, type, rating);

                    response.redirect("/");
                    return "";
                })
        );
    }
}
