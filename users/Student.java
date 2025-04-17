package users;

import java.util.List;
import courses.Course;
import java.util.ArrayList;

public class Student extends User {

    public List<Course> enrolledCourses;
    private int age;
    private String contact;

    public Student(String rollno, String name, int age, String contact, String password) {
        super(rollno, name, password);
        this.age = age;
        this.contact = contact;
        this.enrolledCourses = new ArrayList<>();
    }

    @Override
    public String getrole() {
        return "Student";
    }


    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public void enrollCourse(Course course) {
        if (course != null && !enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
        } else {
            System.out.println("Course already enrolled or invalid course.");
        }
    }

    public void dropCourse(Course course) {
        if (course != null && enrolledCourses.contains(course)) {
            enrolledCourses.remove(course);
        } else {
            System.out.println("Course not found in enrolled courses or invalid course.");
        }
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }
    
}