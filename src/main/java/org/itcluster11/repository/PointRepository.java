package org.itcluster11.repository;

import lombok.extern.slf4j.Slf4j;
import org.itcluster11.model.Category;
import org.itcluster11.model.Point;
import org.itcluster11.model.SearchConfiguration;
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
    private String FIND_LIST_CATEGORIES_OF_POINT = "SELECT c.* FROM Points p JOIN PointToCategory ptc ON ptc.point_id" +
            " = p.id JOIN Category c ON c.id = ptc.category_id WHERE p.id = ?";
    private String FIND_LIST_POINTS_OF_CATEGORY = "SELECT p.* FROM Category c JOIN PointToCategory ptc ON ptc.category_id"
            + " = c.id JOIN Points p ON p.id = ptc.point_id WHERE c.id = ?";
    private String FIND_LIST_POINTS_OF_CATEGORIES_TEMPLATE = "SELECT DISTINCT p.* FROM Category c JOIN PointToCategory ptc ON ptc.category_id"
            + " = c.id JOIN Points p ON p.id = ptc.point_id WHERE c.id IN (";

    private String FIND_LIST_POINTS_OF_RADIUS = "SELECT p.* ,( ACOS( COS( RADIANS( ? ) )* COS( RADIANS( p.latitude ) )* COS( RADIANS( p.longitude)" +
            " - RADIANS( ? ) ) + SIN( RADIANS( ?) )* SIN( RADIANS( p.latitude ) )" +
            "  ) * 6371 ) AS distance_in_km FROM Points p HAVING distance_in_km < ?";

    public void save(Point point) {
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }

            PreparedStatement statement = conn.prepareStatement(INSERT_POINT_SQL);
            statement.setString(1, point.getName());
            statement.setString(2, point.getDescription());
            statement.setDouble(3, point.getLatitude());
            statement.setDouble(4, point.getLongitude());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new point was inserted successfully!");
            }
        } catch (SQLException e) {
            log.error("Point was not inserted to database", e);
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
                Double latitude = result.getDouble("latitude");
                Double longitude = result.getDouble("longitude");

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
            log.error("Reason: ", e);
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
                Double latitude = result.getDouble("latitude");
                Double longitude = result.getDouble("longitude");

                Point point = Point.builder()
                        .id(id)
                        .name(name)
                        .description(description)
                        .latitude(latitude)
                        .longitude(longitude)
                        .categories(getCategoryList(pointid))
                        .build();
                return point;
            }
        } catch (SQLException e) {
            log.error("Reason: ", e);
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
            statement.setDouble(3, point.getLatitude());
            statement.setDouble(4, point.getLongitude());
            statement.setInt(5, point.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing point was updated successfully!");
            }

        } catch (SQLException e) {
            log.error("Reason: ", e);
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
            log.error("Reason: ", e);
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
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Reason: ", e);

        }
    }

    public List<Category> getCategoryList(int point_id) {
        List<Category> categoryList = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }
            PreparedStatement statement = conn.prepareStatement(FIND_LIST_CATEGORIES_OF_POINT);
            statement.setInt(1, point_id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");

                Category category = Category.builder()
                        .id(id)
                        .name(name)
                        .description(description)
                        .build();
                categoryList.add(category);
            }

        } catch (SQLException e) {
            log.error("Reason: ", e);

        }
        return categoryList;

    }

    public List<Point> getPointsList(int category_id) {
        List<Point> pointsList = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }
            PreparedStatement statement = conn.prepareStatement(FIND_LIST_POINTS_OF_CATEGORY);
            statement.setInt(1, category_id);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                Double latitude = result.getDouble("latitude");
                Double longitude = result.getDouble("longitude");

                Point point = Point.builder()
                        .id(id)
                        .name(name)
                        .description(description)
                        .latitude(latitude)
                        .longitude(longitude)
                        .build();
                pointsList.add(point);
            }

        } catch (SQLException e) {
            log.error("Reason: ", e);

        }
        return pointsList;

    }

    public List<Point> findPointsByCategories(List<Category> categories) {
        List<Point> points = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }

            StringBuilder sb = new StringBuilder();
            sb.append(FIND_LIST_POINTS_OF_CATEGORIES_TEMPLATE);
            for (int i = 0; i < categories.size(); i++) {
                sb.append("?,");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(");");
            log.info(sb.toString());
            PreparedStatement statement = conn.prepareStatement(sb.toString());

            for (int i = 1; i <= categories.size(); i++) {
                statement.setInt(i, categories.get(i - 1).getId());
            }


            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                Double latitude = result.getDouble("latitude");
                Double longitude = result.getDouble("longitude");

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
            log.error("Reason: ", e);

        }

        return points;

    }

    public List<Point> findPointsBySearchConfig(SearchConfiguration searchConfiguration) {
        List<Point> points = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }

            StringBuilder sb = new StringBuilder();
            sb.append(FIND_LIST_POINTS_OF_RADIUS);

            PreparedStatement statement = conn.prepareStatement(sb.toString());
            statement.setDouble(1, searchConfiguration.getLat());
            statement.setDouble(2, searchConfiguration.getLng());
            statement.setDouble(3, searchConfiguration.getLat());
            statement.setDouble(4, searchConfiguration.getRadius());

            ResultSet result = statement.executeQuery();

            while (result.next()) {

                int id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");
                Double latitude = result.getDouble("latitude");
                Double longitude = result.getDouble("longitude");

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
            log.error("Reason: ", e);

        }

        return points;

    }
}