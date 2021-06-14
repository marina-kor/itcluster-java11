package org.itcluster11.services;

import org.itcluster11.model.Category;
import org.itcluster11.model.Point;
import org.itcluster11.model.SearchConfiguration;
import org.itcluster11.repository.CategoryRepository;
import org.itcluster11.repository.PointRepository;

import java.util.List;

public class ViewService {
    CategoryRepository categoryRepository = new CategoryRepository();
    PointRepository pointRepository = new PointRepository();
    UserService userService = new UserService();

    public String processLocation(Double latitude, Double longitude, Long userId) {
        SearchConfiguration config = userService.getConfig(userId);
        List<Point> points = pointRepository.findPointsByCategories(config.getCategories());

        return "Зараз вибрані категорії: \n"
                + formatCategoryList(config.getCategories()) + "\n"
                + " Пошук виконується в області: \n"
                + " - " + latitude + ", " + longitude + " \n"
                + " З радіусом: " + config.getRadius() + "\n"
                + " Знайдені локації: \n"
                + formatPointList(points);
    }

    public String processCategory(String categoryName, Long userId) {
        Category category = categoryRepository.findByName(categoryName);
        if (category == null) {
            return "Невідома категорія";
        }

        SearchConfiguration config = userService.refreshSearchConfiguration(userId, category);
        List<Point> points = pointRepository.findPointsByCategories(config.getCategories());

        return "Зараз вибрані категорії: \n"
                + formatCategoryList(config.getCategories())
                + " Знайдені локації: \n"
                + formatPointList(points);
    }

    private String formatCategoryList(List<Category> categories) {
        StringBuilder sb = new StringBuilder();
        for (Category category : categories) {
            sb.append(category.getName());
            sb.append("\n");
        }

        return sb.toString();
    }

    private String formatPointList(List<Point> points) {
        StringBuilder sb = new StringBuilder();
        for (Point point : points) {
            sb.append(point.getName());
            sb.append("\n");
        }

        return sb.toString();
    }
}
