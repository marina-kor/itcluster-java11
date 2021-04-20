package com.mailie.service;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseClient {

    private static Logger lgr = Logger.getLogger(DatabaseClient.class.getName());

    public static void dbCheck(){
        String url = "jdbc:h2:mem:";

        try (Connection con = DriverManager.getConnection(url);
             Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery("SELECT 1+1")) {

            while (rs.next()) {
                System.out.println(rs.getInt(1));
            }

        } catch (SQLException ex) {

            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
