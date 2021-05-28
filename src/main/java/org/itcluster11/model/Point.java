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
    private String longitude;
    private String latitude;
    private List<Category> categories;

}
