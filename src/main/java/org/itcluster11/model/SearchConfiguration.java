package org.itcluster11.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchConfiguration {
    private List<Category> categories;
    private int id;
    private int radius;
    private double lat;
    private double lng;
}
