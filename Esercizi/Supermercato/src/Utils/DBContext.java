package Utils;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/supermercato";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection connessioneDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
