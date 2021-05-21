package org.itcluster11.repository;

import org.itcluster11.model.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PointRepositoryTests {

    @Test
    void shouldCreatePointAndInsertInDatabase() {
        PointRepository repo = new PointRepository();
        repo.save(Point.builder().name("cafe").description("coffee-to-go").build());
        Assertions.assertEquals(1 , repo.findAll().size());
//        for (Point p : repo.findAll()) {
//            System.out.println(p.getName());
//        }
//        Point MyFavoritePoint = repo.findById(15);
//        if (MyFavoritePoint != null) {
//            System.out.println(MyFavoritePoint.getDescription());
//        }
//        Point cafePoint = repo.findById(3);
//        if (cafePoint != null) {
//            cafePoint.setName("lala");
//            repo.update(cafePoint);
//        }
//        repo.delete(3);
    }
}
