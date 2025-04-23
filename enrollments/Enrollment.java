package enrollments;

import courses.Course;
import courses.CourseDAO;
import users.Student;
import util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.Logger;

public class Enrollment {
    public static void enrollInCourse(Student student, Course course){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "INSERT INTO enrollments (roll_no, course_id) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getUserId());
            preparedStatement.setString(2, course.getCourseId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Enrollment successful for student ID: " + student.getUserId() + " in course ID: " + course.getCourseId());
            } else {
                System.out.println("Enrollment failed for student ID: " + student.getUserId() + " in course ID: " + course.getCourseId());
            }
        } catch (SQLException e) {
            Logger.logError("SQL Exception during enrollment: " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.logError("Class not found exception during enrollment: " + ex.getMessage());
        }   
        finally {
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }
    }

    public static void dropCourse(Student student, Course course)  {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "DELETE FROM enrollments WHERE roll_no = ? AND course_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getUserId());
            preparedStatement.setString(2, course.getCourseId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Dropped course ID: " + course.getCourseId() + " for student ID: " + student.getUserId());
            } else {
                System.out.println("Failed to drop course ID: " + course.getCourseId() + " for student ID: " + student.getUserId());
            }
        } catch (SQLException e) {
            Logger.logError("SQL Exception during dropping course: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            Logger.logError("Class not found exception: " + e.getMessage());
        }
         finally {
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }
    }

    public static int getEnrollmentCount(Course course) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            connection = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM enrollments WHERE course_id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, course.getCourseId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            Logger.logError("SQL Exception during getting enrollment count: " + e.getMessage()); 
        } catch (ClassNotFoundException e) {
            Logger.logError("Class not found exception: " + e.getMessage());
        } finally {
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }
        return count;
    }

    public static List<Course> getEnrolledCourses(Student student) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Course> enrolledCourses = new ArrayList<>();
        try {
            connection = DBUtil.getConnection();
            String sql = "SELECT course_id FROM enrollments WHERE roll_no = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String courseId = resultSet.getString("course_id");
                Course course = CourseDAO.getCourseById(courseId); // Assuming you have a method to get course by ID
                if (course != null) {
                    enrolledCourses.add(course);
                }
            }
        } catch (SQLException e) {
            Logger.logError("SQL Exception during getting enrolled courses: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            Logger.logError("Class not found exception: " + e.getMessage());
        } finally {
            DBUtil.close(preparedStatement);
            DBUtil.close(connection);
        }
        return enrolledCourses;
            
    }
    
}