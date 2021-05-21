package org.itcluster11.repository;

import lombok.extern.slf4j.Slf4j;
import org.itcluster11.model.Point;
import org.itcluster11.util.SqlQueries;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PointRepository {
    String dbURL = "jdbc:mysql://localhost:3306/sandbox?useSSL=false";
    String username = "root";
    String password = "root";

    public void save(Point point) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            if (conn != null) {
                System.out.println("Connected");
            }

            PreparedStatement statement = conn.prepareStatement(SqlQueries.INSERT_POINT_SQL);
            statement.setString(1, point.getName());
            statement.setString(2, point.getDescription());
            statement.setString(3, point.getLatitude());
            statement.setString(4, point.getLongitude());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new point was inserted successfully!");
            }
        } catch (SQLException e) {
            log.debug("Point was not inserted to database", e);
        }
    }

    public List<Point> findAll() {
        List<Point> points = new ArrayList<Point>();

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            if (conn != null) {
                System.out.println("Connected");
            }
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(SqlQueries.FIND_ALL_POINTS_SQL);

            int count = 0;

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                String latitude = result.getString("latitude");
                String longitude = result.getString("longitude");

                String output = "User #%d: %s - %s - %s - %s";
                //  System.out.println(String.format(output, ++count, name, description, latitude, longitude));
                Point point = Point.builder()
                        .id(id)
                        .name(name)
                        .description(description)
                        .latitude(latitude)
                        .longitude(longitude)
                        .build();
                points.add(point);
            }
        } catch (SQLException e) {
            log.debug("Reason: ", e);
        }
        return points;
    }

    public Point findById(int pointid) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            if (conn != null) {
                System.out.println("Connected");
            }
            String sql = "SELECT id, name,description, latitude, longitude  FROM Points Where id = ?";
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
                Point point = Point.builder()
                        .id(id)
                        .name(name)
                        .description(description)
                        .latitude(latitude)
                        .longitude(longitude)
                        .build();
                return point;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void update(Point point) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            if (conn != null) {
                System.out.println("Connected");
            }
            String sql = "UPDATE Points SET name=?, description=?, latitude=?, longitude=? WHERE id=?";

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

    public void delete(int id) {
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            if (conn != null) {
                System.out.println("Connected");
            }
            String sql = "DELETE FROM Points WHERE id=?";
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