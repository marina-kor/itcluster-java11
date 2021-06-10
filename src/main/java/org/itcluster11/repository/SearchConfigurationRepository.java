package org.itcluster11.repository;

import lombok.extern.slf4j.Slf4j;
import org.itcluster11.model.Category;
import org.itcluster11.model.SearchConfiguration;
import org.itcluster11.util.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SearchConfigurationRepository {

    private String INSERT_SEARCH_CONFIGURATION_SQL = "INSERT INTO SearchConfig (radius, lng, lat, userId) VALUES (?, ?, ?, ?)";
    private String SELECT_SEARCH_CONFIGURATION_SQL = "SELECT id, radius, lng, lat, userId FROM SearchConfig Where id = ?";
    private String LINK_SEARCH_CONFIGURATION_TO_CATEGORY = "INSERT INTO SearchConfigToCategory (searchConfig_id , category_id) VALUES (?, ?)";
    private String FIND_LIST_CATEGORIES_OF_SEARCH_CONFIGURATION = "SELECT c.* FROM SearchConfig sc JOIN SearchConfigToCategory sctc ON sctc.searchConfig_id" +
            " = sc.id JOIN Category c ON c.id = sctc.category_id WHERE sc.id = ?";
    private String SELECT_SEARCH_CONFIGURATION_SQL_USERID = "SELECT id, radius, lng, lat, userId FROM SearchConfig Where userId = ?";
    private String UNLINK_SEAERCH_CONFIGURATION_TO_CATEGORY ="DELETE FROM SearchConfigToCategory WHERE \n" +
            " searchConfig_id = ? AND category_id  = ?";

    public void save(SearchConfiguration searchConfiguration) {
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }

            PreparedStatement statement = conn.prepareStatement(INSERT_SEARCH_CONFIGURATION_SQL);
            statement.setInt(1, searchConfiguration.getRadius());
            statement.setDouble(2, searchConfiguration.getLng());
            statement.setDouble(3, searchConfiguration.getLat());
            statement.setLong(4, searchConfiguration.getUserId());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new search configuration was inserted successfully!");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    searchConfiguration.setId(generatedKeys.getInt(1));
                }
            }
            for (Category category : searchConfiguration.getCategories()) {
                linkSearchConfigurationToCategory(searchConfiguration.getId(), category.getId());
            }
        } catch (SQLException e) {
            log.debug("Search configuration was not inserted to database", e);
        }
    }


    public SearchConfiguration findById(int searchConfigId) {
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }
            PreparedStatement statement = conn.prepareStatement(SELECT_SEARCH_CONFIGURATION_SQL);
            statement.setInt(1, searchConfigId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                int radius = result.getInt("radius");
                Double lng = result.getDouble("lng");
                Double lat = result.getDouble("lat");
                Long userId= result.getLong("userId");

                SearchConfiguration searchConfiguration = SearchConfiguration.builder()
                        .id(id)
                        .radius(radius)
                        .lng(lng)
                        .lat(lat)
                        .userId(userId)
                        .categories(getCategoryList(searchConfigId))
                        .build();
                return searchConfiguration;
            }
        } catch (SQLException e) {
            log.debug("Reason: ", e);
        }
        return null;
    }

    public void linkSearchConfigurationToCategory(int searchConfig_id, int category_id) {
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }
            PreparedStatement statement = conn.prepareStatement(LINK_SEARCH_CONFIGURATION_TO_CATEGORY);
            statement.setInt(1, searchConfig_id);
            statement.setInt(2, category_id);
            statement.executeUpdate();

        } catch (SQLException e) {
            log.info("Reason: ", e);

        }
    }

    public void unLinkSearchConfigurationToCategory(int searchConfig_id, int category_id) {
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }
            PreparedStatement statement = conn.prepareStatement(UNLINK_SEAERCH_CONFIGURATION_TO_CATEGORY);
            statement.setInt(1, searchConfig_id);
            statement.setInt(2, category_id);
            statement.executeUpdate();

        } catch (SQLException e) {
            log.info("Reason: ", e);

        }
    }

    public List<Category> getCategoryList(int searchConfig_id) {
        List<Category> categoryList = new ArrayList<>();

        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }
            PreparedStatement statement = conn.prepareStatement(FIND_LIST_CATEGORIES_OF_SEARCH_CONFIGURATION);
            statement.setInt(1, searchConfig_id);
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
            log.debug("Reason: ", e);

        }
        return categoryList;

    }

    public SearchConfiguration findByUserId(long searchConfigUserId) {
        try (Connection conn = ConnectionProvider.getConnection()) {

            if (conn != null) {
                System.out.println("Connected");
            }
            PreparedStatement statement = conn.prepareStatement(SELECT_SEARCH_CONFIGURATION_SQL_USERID);
            statement.setLong(1, searchConfigUserId);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int id = result.getInt("id");
                int radius = result.getInt("radius");
                Double lng = result.getDouble("lng");
                Double lat = result.getDouble("lat");
                Long userId= result.getLong("userId");

                SearchConfiguration searchConfiguration = SearchConfiguration.builder()
                        .id(id)
                        .radius(radius)
                        .lng(lng)
                        .lat(lat)
                        .userId(userId)
                        .categories(getCategoryList(id))
                        .build();
                return searchConfiguration;
            }
        } catch (SQLException e) {
            log.debug("Reason: ", e);
        }
        return null;
    }

}
