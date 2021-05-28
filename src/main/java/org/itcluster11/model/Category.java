package org.itcluster11.model;

import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Builder
public class Category {
    private int id;
    private String name;
    private String description;
}
