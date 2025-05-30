package courses;

import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.Logger;

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
            Logger.logError( "SQL Exception during getting all courses: " + e.getMessage());
        }

        return courses;
    }

    public static boolean addCourse(Course course) {
        String sql = "INSERT INTO courses (course_id, name, capacity, type) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getCourseId());
            pstmt.setString(2, course.getCourseName());
            pstmt.setInt(3, course.getCapacity());
            pstmt.setString(4, course.getType().toString().toUpperCase());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            Logger.logError("SQL Exception during adding course: " + e.getMessage());
        }

        return false;
    }

    public static boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET name = ?, capacity = ?, type = ? WHERE course_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, course.getCourseName());
            pstmt.setInt(2, course.getCapacity());
            pstmt.setString(3, course.getType().toString().toUpperCase());
            pstmt.setString(4, course.getCourseId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException | ClassNotFoundException e) {
            Logger.logError("SQL Exception during updating course: " + e.getMessage());
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
            Logger.logError("SQL Exception during removing course: " + e.getMessage());
        }

        return false;
    }

    public static Course getCourseById(String courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        Course course = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("course_id");
                String name = rs.getString("name");
                int capacity = rs.getInt("capacity");
                String typeStr = rs.getString("type");

                // Mapping MySQL ENUM → Java ENUM
                CourseType type = CourseType.valueOf(typeStr.toUpperCase());

                course = new Course(id, name, capacity, type);
            }

        } catch (SQLException | ClassNotFoundException e) {
            Logger.logError("SQL Exception during getting course by ID: " + e.getMessage());
        }

        return course;
    }
}
