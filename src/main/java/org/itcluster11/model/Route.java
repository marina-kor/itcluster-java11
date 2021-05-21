package org.itcluster11.model;

import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
@Setter
public class Route {
   private String name;
   private String description;
   private List<Point> points;
}
