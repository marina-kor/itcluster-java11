package org.itcluster11.repository;

import lombok.extern.slf4j.Slf4j;
import org.itcluster11.model.Category;
import org.itcluster11.util.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CategoryRepository {
    private static final String INSERT_CATEGORY = "INSERT INTO Category (name, description) VALUES (?, ?) ";
    private String FIND_ALL_CATEGORIES_SQL = "SELECT id, name,description FROM Category";
    private String SELECT_CATEGORY_SQL = "SELECT id, name, description  FROM Category WHERE id = ?";
    private String UPDATE_CATEGORY_SQL = "UPDATE Category SET name=?, description=? WHERE id=?";
    private String DELETE_CATEGORY_SQL = "UPDATE Category SET name=?, description=? WHERE id=?";

    public void save(Category category) {
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }

            PreparedStatement statement = conn.prepareStatement(INSERT_CATEGORY);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new point category was inserted successfully!");
            }
        } catch (SQLException e) {
            log.debug("Point category was not inserted to database", e);
        }
    }

    public List<Category> findAll() {
        List<Category> categories = new ArrayList<Category>();

        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(FIND_ALL_CATEGORIES_SQL);

            int count = 0;

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");

                String output = "User #%d: %s - %s - %s - %s";
                Category category = Category.builder()
                        .id(id)
                        .name(name)
                        .description(description)
                        .build();
                categories.add(category);
            }
        } catch (SQLException e) {
            log.debug("Reason: ", e);
        }
        return categories;
    }

    public Category findById(int categoryId) {
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }
            PreparedStatement statement = conn.prepareStatement(SELECT_CATEGORY_SQL);
            statement.setInt(1, categoryId);
            ResultSet result = statement.executeQuery();

            int count = 0;

            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String description = result.getString("description");

                String output = "User #%d: %s - %s - %s - %s";
                Category category = Category.builder()
                        .id(id)
                        .name(name)
                        .description(description)
                        .build();
                return category;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void update(Category category) {
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }


            PreparedStatement statement = conn.prepareStatement(UPDATE_CATEGORY_SQL);
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing category was updated successfully!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }

            PreparedStatement statement = conn.prepareStatement(DELETE_CATEGORY_SQL);
            statement.setInt(1, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing category was deleted successfully!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
