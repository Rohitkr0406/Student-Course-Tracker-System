package users;

import java.util.List;
import courses.Course;
import courses.CourseDAO;

import java.util.ArrayList;

public class Admin extends User {

    public List<Course> courses;

    public Admin(String id, String name, String password) {
        super(id, name, password);
        this.courses = new ArrayList<>();
    }

    @Override
    public String getrole() {
        return "Admin";
    }

    public void addCourse(Course course) {
        if (course != null && !courses.contains(course)) {
            courses.add(course);
            boolean added = CourseDAO.addCourse(course);
            if (added) {
                System.out.println("Course added successfully.");
            } else {
                System.out.println("Failed to add course to the database.");
            }
        } else {
            System.out.println("Course already exists or invalid course.");
        }
    }

    public void removeCourse(Course course) {
        if (course != null && courses.contains(course)) {
            courses.remove(course);
            boolean removed = CourseDAO.removeCourse(course);
            if (removed) {
                System.out.println("Course removed successfully.");
            } else {
                System.out.println("Failed to remove course from the database.");
            }
        } else {
            System.out.println("Course not found in existing courses or invalid course.");
        }
    }

    public void  updateCourse(Course oldCourse, Course newCourse) {
        int index = courses.indexOf(oldCourse);
        if(index != -1) {
            courses.set(index, newCourse);
            boolean updated = CourseDAO.updateCourse(newCourse);
            if(updated) {
                System.out.println("Course updated successfully.");
            } else {
                System.out.println("Failed to update course in the database.");
            }
        } else {
            System.out.println("Course not found in existing courses.");
        }
    }

    public void viewAllStudents(){
        List<Student> students = StudentDAO.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("List of all students:");
            System.out.println("Name\tID");
            System.out.println("---------------------");
            for (Student student : students) {
                System.out.println(student.getUserId() + "\t" + student.getName());
            }
        }
    }
}


