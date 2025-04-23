package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DBUtil {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/student_course_tracker";
    public static final String USER = "root";
    public static final String PASS = "_rohit_kr0";
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void close(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}