import com.mailie.service.DatabaseClient;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailieApplication {
    public static void main(String[] args) throws SQLException {
       DatabaseClient.dbCheck();
    }
}
