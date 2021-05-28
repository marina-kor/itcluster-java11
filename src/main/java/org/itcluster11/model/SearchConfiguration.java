package org.itcluster11.model;

import lombok.Data;

import java.util.List;

@Data
public class SearchConfiguration {
    private List<Category> categories;
    private int radius;
    private double lat;
    private double lng;
}
