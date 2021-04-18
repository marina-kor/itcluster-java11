package com.mailie.model;

import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@Setter
public class Point {
    private String name;
    private String description;
    private String longitude;
    private String latitude;
    private List<PointCategory> categories;
    
}
