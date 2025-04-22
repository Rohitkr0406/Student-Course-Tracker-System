package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GradeDAO {

    //Assign or update a grade for a student in a course
    public static boolean assignGrade(String roll_no, String courseId, GradeType grade) {
        String sql = "UPDATE enrollments SET grade = ? WHERE roll_no = ? AND course_id = ?";

        try(Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, grade.toString().toUpperCase());
            pstmt.setString(2, roll_no);
            pstmt.setString(3, courseId);
            
            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected > 0) {
                System.out.println("Successfully assigned grade " + grade + " to student " + roll_no + " for course " + courseId);
                Logger.logAction(roll_no, "Assigned grade " + grade + " for course " + courseId);
                return true;
            } else {
                System.out.println("Failed to assign grade. No matching enrollment found.");
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // proper logging can be added later
        }
        return false;
    }

    //Get all grades for a student
    public static List<EnrollmentGrade> getGradesByStudentId(String roll_no) {
        List<EnrollmentGrade> grades = new ArrayList<>();
        String sql = "SELECT * FROM enrollments WHERE student_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, roll_no);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String courseId = rs.getString("course_id");
                String gradestr = rs.getString("grade");
                GradeType grade = gradestr != null ? GradeType.valueOf(gradestr.toUpperCase()) : null; 
                grades.add(new EnrollmentGrade(roll_no, courseId, grade));
            }
            Logger.logAction(roll_no, "Retrived grades for student. ");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // proper logging can be added later
        }
        return grades;

    }


    //Helper class to represent a grade record
    public static class EnrollmentGrade{
        private String roll_no;
        private String courseId;
        private GradeType grade;

        public EnrollmentGrade(String roll_no, String courseId, GradeType grade) {
            this.roll_no = roll_no;
            this.courseId = courseId;
            this.grade = grade;
        }

        public String getStudentId() {
            return roll_no;
        }

        public String getCourseId() {
            return courseId;
        }

        public Enum<GradeType> getGrade() {
            return grade;
        }
    }
}
