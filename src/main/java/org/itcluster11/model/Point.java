package org.itcluster11.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Point {
    private int id;
    private String name;
    private String description;
    private Double longitude;
    private Double latitude;
    private List<Category> categories;

}
