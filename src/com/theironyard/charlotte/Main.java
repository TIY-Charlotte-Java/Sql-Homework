package com.theironyard.charlotte;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            Server.createWebServer().start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("hello");
        // creates connection object to build statements
        Connection conn = DriverManager.getConnection("jdbc:h2:./main");
    }
}
