package courses;

import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public static List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM courses")) {

            while (rs.next()) {
                String id = rs.getString("course_id");
                String name = rs.getString("name");
                int capacity = rs.getInt("capacity");
                String typeStr = rs.getString("type");

                // Mapping MySQL ENUM → Java ENUM
                CourseType type = CourseType.valueOf(typeStr.toUpperCase());

                Course course = new Course(id, name, capacity, type);
                courses.add(course);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // proper logging can be added later
        }

        return courses;
    }

    public static boolean addCourse(Course course) {
        String sql = "INSERT INTO courses (course_id, name, capacity, type) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getCourseId());
            pstmt.setString(2, course.getName());
            pstmt.setInt(3, course.getCapacity());
            pstmt.setString(4, course.getType().toString().toUpperCase());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // proper logging can be added later
        }

        return false;
    }

    public static boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET name = ?, capacity = ?, type = ? WHERE course_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getName());
            pstmt.setInt(2, course.getCapacity());
            pstmt.setString(3, course.getType().toString().toUpperCase());
            pstmt.setString(4, course.getCourseId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // proper logging can be added later
        }

        return false;
    }

    public static boolean removeCourse(Course course) {
        String sql = "DELETE FROM courses WHERE course_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getCourseId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // proper logging can be added later
        }

        return false;
    }
}
