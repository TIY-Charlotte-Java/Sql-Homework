package com.theironyard.charlotte;

import org.h2.tools.Server;
import spark.Session;
import spark.Spark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws SQLException {

        Server.createWebServer().start();
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");

        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS restaurants (id IDENTITY, name VARCHAR, type VARCHAR, rating INT)");
        stmt.execute("INSERT INTO restaurants VALUES (NULL, 'McDonalds', 'Fast Food', 2)");

        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {
                    Session session = request.session();

                })
        );

    }
}
