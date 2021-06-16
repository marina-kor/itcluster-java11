package org.itcluster11.services;

import lombok.extern.slf4j.Slf4j;
import org.itcluster11.model.Category;
import org.itcluster11.model.SearchConfiguration;
import org.itcluster11.repository.SearchConfigurationRepository;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.ArrayList;

@Slf4j
public class UserService {

    SearchConfigurationRepository searchConfigurationRepository = new SearchConfigurationRepository();

    public static String getUserName(User user) {
        return (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    public SearchConfiguration refreshSearchConfiguration(Long userId, Category category) {
        SearchConfiguration config = getConfig(userId);

        if (config.getCategories().contains(category)) {
            searchConfigurationRepository.unLinkSearchConfigurationToCategory(config.getId(), category.getId());
        } else {
            searchConfigurationRepository.linkSearchConfigurationToCategory(config.getId(), category.getId());
        }

        config = searchConfigurationRepository.findById(config.getId());
        return config;
    }

    public SearchConfiguration refreshSearchConfiguration(Long userId, Double longitude, Double latitude) {
        SearchConfiguration config = getConfig(userId);

        config.setLng(longitude);
        config.setLat(latitude);

        searchConfigurationRepository.save(config);

        config = searchConfigurationRepository.findById(config.getId());
        return config;
    }
    public SearchConfiguration refreshSearchConfiguration(Long userId, int radius) {
        SearchConfiguration config = getConfig(userId);

        config.setRadius(radius);

        searchConfigurationRepository.save(config);
        config = searchConfigurationRepository.findById(config.getId());
        return config;}

    public SearchConfiguration getConfig(Long userId) {
        SearchConfiguration config = searchConfigurationRepository.findByUserId(userId);

        if (config == null) {
            config = SearchConfiguration
                    .builder()
                    .userId(userId)
                    .categories(new ArrayList<>())
                    .radius(5)
                    .build();
            searchConfigurationRepository.save(config);
        }
        return config;
    }
}