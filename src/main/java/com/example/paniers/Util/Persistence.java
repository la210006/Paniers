package com.example.paniers.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Persistence {

    private static final String URL = "jdbc:sqlite:C:/sqlite/db/paniers.db"; // Chemin correct vers la base de données

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load SQLite JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
