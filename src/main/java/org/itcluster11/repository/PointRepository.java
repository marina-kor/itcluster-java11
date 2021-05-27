package org.itcluster11.repository;

import lombok.extern.slf4j.Slf4j;
import org.itcluster11.model.Point;
import org.itcluster11.util.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PointRepository {
    private String INSERT_POINT_SQL = "INSERT INTO Points (name, description, latitude, longitude) VALUES (?, ?, ?, ?)";
    private String FIND_ALL_POINTS_SQL = "SELECT id, name,description, latitude, longitude FROM Points";
    private String SELECT_POINT_SQL = "SELECT id, name,description, latitude, longitude  FROM Points Where id = ?";
    private String UPDATE_POINT_SQL = "UPDATE Points SET name=?, description=?, latitude=?, longitude=? WHERE id=?";
    private String DELETE_POINT_SQL = "DELETE FROM Points WHERE  id=?";
    private String LINK_POINT_TO_CATEGORY = "INSERT INTO PointToCategory (point_id , category_id) VALUES (?, ?)";


    public void save(Point point) {
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }

            PreparedStatement statement = conn.prepareStatement(INSERT_POINT_SQL);
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

        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(FIND_ALL_POINTS_SQL);

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
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }
            PreparedStatement statement = conn.prepareStatement(SELECT_POINT_SQL);
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
        } catch (SQLException e) {
            log.debug("Reason: ", e);
        }
        return null;
    }

    public void update(Point point) {
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }


            PreparedStatement statement = conn.prepareStatement(UPDATE_POINT_SQL);
            statement.setString(1, point.getName());
            statement.setString(2, point.getDescription());
            statement.setString(3, point.getLatitude());
            statement.setString(4, point.getLongitude());
            statement.setInt(5, point.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing point was updated successfully!");
            }

        } catch (SQLException e) {
            log.debug("Reason: ", e);
        }
    }

    public void delete(int id) {
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }

            PreparedStatement statement = conn.prepareStatement(DELETE_POINT_SQL);
            statement.setInt(1, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing point was deleted successfully!");
            }

        } catch (SQLException e) {
            log.debug("Reason: ", e);
        }
    }

    public void linkCategoryToPoint(int point_id, int category_id) {
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }
            PreparedStatement statement = conn.prepareStatement(LINK_POINT_TO_CATEGORY);
            statement.setInt(1, point_id);
            statement.setInt(2, category_id);
            ResultSet result = statement.executeQuery();

        } catch (SQLException e) {
            log.debug("Reason: ", e);

        }
    }
}