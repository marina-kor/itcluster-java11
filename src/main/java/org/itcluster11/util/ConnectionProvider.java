package org.itcluster11.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
    static String dbURL = "jdbc:mysql://localhost:3306/sandbox?useSSL=false";
    static String username = "root";
    static String password = "root";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbURL, username, password);
    }
}
