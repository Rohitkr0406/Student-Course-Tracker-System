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

    public List<Course> getEnrolledCourses() { return enrolledCourses; }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public void removeEnrolledCourse(Course course) {
        if (enrolledCourses.contains(course)) {
            enrolledCourses.remove(course);
        }
        else{
            System.out.println("Course not found in enrolled courses.");
        }
    }

    public void viewEnrolledCourses() {
        if (enrolledCourses.isEmpty()) {
            System.out.println("No courses enrolled.");
        } else {
            System.out.println("Enrolled Courses:\n");
            System.out.println("Course ID:\tCourse Name");
            System.out.println("-------------------------------------------------");
            for(Course course : enrolledCourses) {
                System.out.println(course.getCourseId() + ":\t" + course.getCourseName());
            }
        }
    }
    
}