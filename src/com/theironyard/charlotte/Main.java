package com.theironyard.charlotte;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.h2.tools.Server;
import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static Restaurant insertRestaurant(Connection conn, String name, String phone, String type, boolean isOpen) throws SQLException {
        Restaurant restaurant = new Restaurant();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO restaurants VALUES (null, ?, ?, ?, ?)");
        stmt.setString(1, name);
        stmt.setString(2, phone);
        stmt.setString(3, type);
        stmt.setBoolean(4, isOpen);
        stmt.execute();
        return restaurant;
    }

    public static Restaurant getRestaurantById(Connection conn, int id) throws SQLException {
        Restaurant rest = null;
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM restaurants WHERE id = ?");
        stmt.setInt(1, id);

        ResultSet result = stmt.executeQuery();

        if (result.next()) {
            String name = result.getString("name");
            String phone = result.getString("phone");
            String type = result.getString("type");
            Boolean isOpen = result.getBoolean("is_open");
            rest = new Restaurant(id, name, phone, type, isOpen);
        }
        return rest;
    }

    public static Restaurant updateRestaurant(Connection conn, int id, String name, String phone, String type, boolean isOpen) throws SQLException {
        Restaurant current = getRestaurantById(conn, id);
        PreparedStatement stmt = conn.prepareStatement("UPDATE restaurants SET name=?, phone=?, type=?, is_open=? WHERE id = ?");
        stmt.setInt(5, id);
        stmt.setString(1, name);
        stmt.setString(2, phone);
        stmt.setString(3, type);
        stmt.setBoolean(4, isOpen);
        stmt.execute();
        return current;
    }

    public static void deleteRestaurant(Connection conn, int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM restaurants WHERE ID = ?");
        stmt.setInt(1, id);
        stmt.execute();
    }

    private static ArrayList<Restaurant> selectRestaurant(Connection conn) throws SQLException {
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery("SELECT * FROM restaurants");
        while (results.next()) {
            int id = results.getInt("id");
            String name = results.getString("name");
            String phone = results.getString("phone");
            String type = results.getString("type");
            Boolean isDone = results.getBoolean("is_Open");
            restaurants.add(new Restaurant(id, name, phone, type, isDone));
        }
        return restaurants;
    }

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS restaurants (id IDENTITY, name VARCHAR, phone VARCHAR, type VARCHAR, is_open BOOLEAN)");

        stmt.execute("SELECT * FROM restaurants ORDER BY ID");

        //selectRestaurant(conn).add(new Restaurant(1, "Papa Johns", "2348230428340", "Pizza", true));

        Spark.post("/create-restaurant", (req, res) -> {
            String name = req.queryParams("name");
            String phone = req.queryParams("phone");
            String type = req.queryParams("type");
            Boolean isOpen = Boolean.valueOf(req.queryParams("isOpen"));
            Restaurant newRestaurant = insertRestaurant(conn, name, phone, type, isOpen);

            newRestaurant.setId(Integer.valueOf(req.queryParams("id")));
            newRestaurant.setName(req.queryParams("name"));
            newRestaurant.setPhone(req.queryParams("phone"));
            newRestaurant.setType(req.queryParams("type"));
            newRestaurant.setOpen(Boolean.valueOf(req.queryParams("isOpen")));

            insertRestaurant(conn, name, phone, type, isOpen);
//                    new Restaurant(
//                            Integer.valueOf(req.queryParams("id")),
//                            req.queryParams("name"),
//                            req.queryParams("phone"),
//                            req.queryParams("type"),
//                            Boolean.valueOf(req.queryParams("isOpen"))));
//            PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO restaurants VALUES (null, ?, ?, ?, ?)");
//            stmt1.setString(1, req.queryParams("name"));
//            stmt1.setString(2, req.queryParams("phone"));
//            stmt1.setString(3, req.queryParams("type"));
//            stmt1.setBoolean(4, Boolean.valueOf(req.queryParams("isOpen")));
//            stmt1.execute();
            res.redirect("/");
            return "";
        });

        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    m.put("restaurants", selectRestaurant(conn));
                    return new ModelAndView(m, "restaurants.html");
                }), new MustacheTemplateEngine());

        Spark.get("/restaurants/:id", (req, res) -> {
            int id = Integer.valueOf(req.params("id"));
            Restaurant current = getRestaurantById(conn, id);
            HashMap m = new HashMap();
            if (current != null) {
                m.put("restaurant", current);

            }
            return new ModelAndView(m, "restaurant.html");
        }, new MustacheTemplateEngine());

        Spark.post("/restaurants/:id", (req, res) -> {
            int id = Integer.valueOf(req.params("id"));

            Restaurant current = getRestaurantById(conn, id);

            current.setId(Integer.valueOf(req.queryParams("id")));
            current.setName(req.queryParams("name"));
            current.setPhone(req.queryParams("phone"));
            current.setType(req.queryParams("type"));
            current.setOpen(Boolean.valueOf(req.queryParams("isOpen")));

            updateRestaurant(conn, current.id, current.name, current.phone, current.type, current.isOpen);

            res.redirect("/");

            return "";
        });

        Spark.post("/delete-restaurant", (req, res) -> {
            int id = Integer.valueOf(req.queryParams("id"));

            deleteRestaurant(conn, id);

            res.redirect("/");

            return "";

        });
    }
}
