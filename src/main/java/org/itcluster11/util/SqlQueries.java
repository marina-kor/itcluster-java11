package org.itcluster11.util;

public interface SqlQueries {
    // Point
    String INSERT_POINT_SQL = "INSERT INTO Points (name, description, latitude, longitude) VALUES (?, ?, ?, ?)";
    String FIND_ALL_POINTS_SQL = "SELECT id, name,description, latitude, longitude FROM Points";
}
