package org.itcluster11.model;

import lombok.Data;

import java.util.List;

@Data
public class Profile {
    private long id;
    private List<String> chatIds;
    private List<SearchConfiguration> searchConfigurations;
}
