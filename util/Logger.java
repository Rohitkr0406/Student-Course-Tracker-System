package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Logger{
    
    public static void logAction(String userId, String action){
        String sql = "INSERT INTO logs (user_id, action) VALUES (?, ?)";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, userId);
            pstmt.setString(2, action);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("SQL Exception during logging: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found exception: " + e.getMessage());
        }
    }

    public static void logError(String errorMessage){
        String truncatedMessage = errorMessage.length() > 255 ? errorMessage.substring(0, 255) + "..." : errorMessage;
        String sql = "INSERT INTO logs (user_id, action) VALUES (?, ?)";
        try(Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, "system");
            pstmt.setString(2, truncatedMessage);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println("SQL Exception during logging error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found exception: " + e.getMessage());
        }
    }
}