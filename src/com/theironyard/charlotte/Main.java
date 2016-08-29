package com.theironyard.charlotte;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws SQLException {
        Server.createWebServer().start();

        Connection conn = DriverManager.getConnection("jdbc:h2:./main");

        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS players (id IDENTITY, name VARCHAR, is_alive BOOLEAN, score INT, health DOUBLE)");
        stmt.execute("INSERT INTO players VALUES (NULL, 'Alice', true, 0, 100.0)");
        stmt.execute("UPDATE players SET is_alive = FALSE WHERE name = 'Alice'");
        stmt.execute("DELETE FROM players WHERE name = 'Alice'");
    }
}
