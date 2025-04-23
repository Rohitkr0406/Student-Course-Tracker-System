package users;

import util.DBUtil;
import java.sql.*;
import util.Logger;

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
            Logger.logError("Error occurred while retrieving admin with emp_id: " + emp_id + ". Details: " + e.getMessage());
        }
        return null; // In case of an error, return null
    }
}