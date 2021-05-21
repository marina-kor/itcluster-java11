import com.mailie.dao.PointDAO;
import com.mailie.model.Point;

import java.sql.SQLException;

public class MailieApplication {
    public static void main(String[] args) throws SQLException {
        // DatabaseClient.dbCheck();
        PointDAO pd = new PointDAO();
        Point point = new Point();
        point.setName("cafe");
        point.setDescription("coffee-to-go");
        pd.insertPoint(point);
        for (Point p : pd.selectPoint()) {
            System.out.println(p.getName());
        }
        Point MyFavoritePoint = pd.selectPointByID(15);
        if (MyFavoritePoint != null) {
            System.out.println(MyFavoritePoint.getDescription());
        }
        Point cafePoint = pd.selectPointByID(3);
        if (cafePoint != null) {
            cafePoint.setName("lala");
            pd.updatePoint(cafePoint);
        }
        pd.deletePoint(3);
    }
}
