package users;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DBUtil;

public class StudentDAO {

    public static List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {

            while (rs.next()) {
                String rollNo = rs.getString("roll_no");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String contact = rs.getString("contact");
                String password = rs.getString("password");

                Student student = new Student(rollNo, name, age, contact, password);
                students.add(student);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // proper logging can be added later
        }
        return students;       
    }

    public static Student getStudentByIdAndPassword(String rollNo, String password) {
        Student student = null;
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement ptsmt = conn.prepareStatement("SELECT * FROM students WHERE roll_no = ? AND password = ?")) {
            ptsmt.setString(1, rollNo);
            ptsmt.setString(2, password);
            ResultSet rs = ptsmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String contact = rs.getString("contact");

                student = new Student(rollNo, name, age, contact, password);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // proper logging can be added later
        }
        return student;
    }
}