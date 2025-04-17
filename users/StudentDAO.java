package users;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Statement;
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
}