package users;

import util.DBUtil;
import java.sql.*;

public class AdminDAO{

    public static Admin getAdminByIdAndPassword(String emp_id, String password) {
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM admins WHERE emp_id = ? AND password = ?")) {
            
            pstmt.setString(1, emp_id);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String name = rs.getString("name");
                return new Admin(emp_id, name, password);
            } else {
                return null; // No admin found with the given credentials
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // proper logging can be added later
        }
        return null; // In case of an error, return null
    }
}