package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/student_couuse_tracker";
    public static final String USER = "root";
    public static final String PASS = "_rohit_kr0";
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}