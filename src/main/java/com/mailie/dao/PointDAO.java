package com.mailie.dao;

import com.mailie.model.Point;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PointDAO {
    String dbURL = "jdbc:mysql://localhost:3306/Mailie_db?useSSL=false";
    String username = "root";
    String password = "root";


    public void insertPoint(Point point) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            if (conn != null) {
                System.out.println("Connected");
            }


            String sql = "INSERT INTO Point (name, description, latitude, longitude) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, point.getName());
            statement.setString(2, point.getDescription());
            statement.setString(3, point.getLatitude());
            statement.setString(4, point.getLongitude());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new point was inserted successfully!");
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public List<Point> selectPoint() {
        List<Point> points = new ArrayList<Point>();

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            if (conn != null) {
                System.out.println("Connected");
            }
            String sql = "SELECT id, name,description, latitude, longitude  FROM Point";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            int count = 0;

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                String latitude = result.getString("latitude");
                String longitude = result.getString("longitude");

                String output = "User #%d: %s - %s - %s - %s";
                //  System.out.println(String.format(output, ++count, name, description, latitude, longitude));
                Point point = new Point();
                point.setId(id);
                point.setName(name);
                point.setDescription(description);
                point.setLatitude(latitude);
                point.setLongitude(longitude);
                points.add(point);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return points;
    }

    public Point selectPointByID(int pointid) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            if (conn != null) {
                System.out.println("Connected");
            }
            String sql = "SELECT id, name,description, latitude, longitude  FROM Point Where id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, pointid);
            ResultSet result = statement.executeQuery();

            int count = 0;

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                String latitude = result.getString("latitude");
                String longitude = result.getString("longitude");

                String output = "User #%d: %s - %s - %s - %s";
                //  System.out.println(String.format(output, ++count, name, description, latitude, longitude));
                Point point = new Point();
                point.setId(id);
                point.setName(name);
                point.setDescription(description);
                point.setLatitude(latitude);
                point.setLongitude(longitude);
                return point;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void updatePoint(Point point) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            if (conn != null) {
                System.out.println("Connected");
            }
            String sql = "UPDATE Point SET name=?, description=?, latitude=?, longitude=? WHERE id=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, point.getName());
            statement.setString(2, point.getDescription());
            statement.setString(3, point.getLatitude());
            statement.setString(4, point.getLongitude());
            statement.setInt(5, point.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing point was updated successfully!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deletePoint(int id) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            if (conn != null) {
                System.out.println("Connected");
            }
            String sql = "DELETE FROM Point WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing point was deleted successfully!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}