package com.theironyard.charlotte;

import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws SQLException {
	// write your code here
        Server.createWebServer().start();

        Connection conn = DriverManager.getConnection("jdbc:h2:./main");

        Statement stmt = conn.createStatement();

        stmt.execute("CREATE TABLE IF NOT EXISTS restaurants (id IDENTITY, name VARCHAR, type VARCHAR, rating INT)");

        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap m = new HashMap();

                    m.put("restaurants", Restaurant.selectRestaurants(conn));

                    return new ModelAndView(m, "home.html");
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/create-restaurant",
                ((request, response) -> {
                    String name = request.queryParams("name");
                    String type = request.queryParams("type");
                    int rating = Integer.valueOf(request.queryParams("rating"));

                    Restaurant.insertRestaurant(conn, name, type, rating);

                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/delete-restaurant/:id",
                ((request, response) -> {
                    int id = Integer.valueOf(request.params("id"));

                    Restaurant.deleteRestaurant(conn, id);

                    response.redirect("/");
                    return "";
                })
        );

        Spark.get(
                "/update-restaurant/:id",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    int id = Integer.valueOf(request.params("id"));

                    m.put("id", id);

                    return new ModelAndView(m, "edit.html");
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/update-restaurant/:id",
                ((request, response) -> {
                    String name = request.queryParams("name");
                    String type = request.queryParams("type");
                    int rating = Integer.valueOf(request.queryParams("rating"));
                    int id = Integer.valueOf(request.params("id"));

                    Restaurant.updateRestaurant(conn, name, type, rating, id);

                    response.redirect("/");
                    return "";
                })
        );
    }
}
