import courses.*;
import users.*;
import enrollments.*;
import util.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.println("Welcome to the Course Management System!");
                System.out.println("1. Login as Student");
                System.out.println("2. Login as Admin");
                System.out.println("3. Exit");
                System.out.print("Please select an option: ");
                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                if (choice == 1) {
                    handleStudentLogin(sc);
                } else if (choice == 2) {
                    handleAdminLogin(sc);
                } else if (choice == 3) {
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
                
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void handleAdminLogin(Scanner sc) {
        System.out.print("Enter Admin ID: ");
        String adminId = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        Admin admin = AdminDAO.getAdminByIdAndPassword(adminId, password);
        if (admin == null) {
            System.out.println("Invalid Admin ID or Password. Please try again.");
            Logger.logAction(adminId, "Failed login attempt");
            return;
        }

        Logger.logAction(adminId, "Logged in successfully");
        System.out.println("Welcome, " + admin.getName() + "! You are logged in as Admin.");
        while(true){
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Add Course");
            System.out.println("2. Remove Course");
            System.out.println("3. Update Course");
            System.out.println("4. View All Students");
            System.out.println("5. View All Courses");
            System.out.println("6. Assign Grade to Student");
            System.out.println("7. Logout");
            System.out.print("\n Select an option: ");
            int adminChoice = Integer.parseInt(sc.nextLine());

            if(adminChoice == 1){
                System.out.print("Enter Course ID: ");
                String courseId = sc.nextLine();
                System.out.print("Enter Course Name: ");
                String courseName = sc.nextLine();
                System.out.print("Enter Capacity: ");
                int capacity;
                try{
                    capacity = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid capacity. Please enter a number.");
                    continue;
                }
                System.out.print("Enter Type (CORE/ELECTIVE): ");
                CourseType type;
                try{
                    type = CourseType.valueOf(sc.nextLine().toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid course type. Please enter CORE or ELECTIVE.");
                    continue;
                }
                Course course = new Course(courseId, courseName, capacity, type);
                admin.addCourse(course);
                Logger.logAction(adminId, "Added course: " + courseId);
            } 

            else if(adminChoice == 2){
                System.out.print("Enter Course ID to remove: ");
                String courseId = sc.nextLine();
                Course course = CourseDAO.getAllCourses().stream()
                    .filter(c-> c.getCourseId().equals(courseId))
                    .findFirst()
                    .orElse(null);
                if(course != null){
                    admin.removeCourse(course);
                    Logger.logAction(adminId, "Removed Course " + courseId);
                } else {
                    System.out.println("Course not found.");
                }
            }

            else if(adminChoice == 3){
                System.out.print("Enter Course ID to update: ");
                String courseId = sc.nextLine();
                Course oldCourse = CourseDAO.getAllCourses().stream()
                    .filter(c-> c.getCourseId().equals(courseId))
                    .findFirst()
                    .orElse(null);
                if(oldCourse != null){
                    System.out.print("Enter new Course Name: ");
                    String courseName = sc.nextLine();
                    System.out.print("Enter new Capacity: ");
                    int capacity;
                    try{
                        capacity = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid capacity. Please enter a number.");
                        continue;
                    }
                    System.out.print("Enter new Type (CORE/ELECTIVE): ");
                    CourseType type;
                    try{
                        type = CourseType.valueOf(sc.nextLine().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid course type. Please enter CORE or ELECTIVE.");
                        continue;
                    }
                    Course newCourse = new Course(courseId, courseName, capacity, type);
                    admin.updateCourse(oldCourse, newCourse);
                    Logger.logAction(adminId, "Updated course: " + courseId);
                    } else {
                        System.out.println("Course not found.");
                    }
                }
                
                else if(adminChoice == 4){
                    admin.viewAllStudents();
                    Logger.logAction(adminId, "Viewed all students.");
                }

                else if(adminChoice == 5){
                    admin.viewAllCourses();
                    Logger.logAction(adminId, "Viewed all courses.");
                }
                
                else if(adminChoice == 6){
                    System.out.print("Enter Student ID: ");
                    String studentId = sc.nextLine();
                    System.out.print("Enter Course ID: ");
                    String courseId = sc.nextLine();
                    System.out.print("Enter Grade (A/B/C/D/F): ");
                    String gradeStr = sc.nextLine().toUpperCase();
                    GradeType grade;
                    try{
                        grade = GradeType.valueOf(gradeStr);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid grade. Please enter A, B, C, D, or F.");
                        continue;
                    }
                    boolean success = GradeDAO.assignGrade(studentId, courseId, grade);
                    if(success) {
                        Logger.logAction(adminId, "Assigned grade " + grade + " to student " + studentId + " for course " + courseId);
                    }
                }
                    
                else if(adminChoice == 7){
                    System.out.println("Logging out...");
                    Logger.logAction(adminId, "Logged out successfully");
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
                
        }

    private static void handleStudentLogin(Scanner sc) {
        System.out.print("Enter Student ID: ");
        String studentId = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        Student student = StudentDAO.getStudentByIdAndPassword(studentId, password);
        if (student == null) {
            System.out.println("Invalid Student ID or Password. Please try again.");
            Logger.logAction(studentId, "Failed login attempt");
            return;
        }

        Logger.logAction(studentId, "Logged in successfully");
        System.out.println("Welcome, " + student.getName() + "! You are logged in as Student.");

        while(true){
            System.out.println("\nStudent Menu:");
            System.out.println("1. View All Courses");
            System.out.println("2. View Enrolled Courses");
            System.out.println("3. Enroll in Course");
            System.out.println("4. Drop Course");
            System.out.println("5. View Report Card");
            System.out.println("6. Logout");
            System.out.print("\n Select an option: ");
            int studentChoice = Integer.parseInt(sc.nextLine());

            if(studentChoice == 1){
                List<Course> allCourses = CourseDAO.getAllCourses();
                System.out.println("Available Courses:");
                System.out.printf("%-10s %-50s %-10s %-15s\n", "Course ID", "Course Name", "Capacity", "Type");
                System.out.println("-".repeat(80));
                for(Course course : allCourses){
                    System.out.printf("%-10s %-50s %-10d %-15s\n", course.getCourseId(), course.getCourseName(), course.getCapacity(), course.getType());
                }
                System.out.println("-".repeat(80));
                System.out.println("Total Courses Available: " + allCourses.size());
            } 
            
            else if(studentChoice == 2){
                List<Course> enrolledCourses = Enrollment.getEnrolledCourses(student);
                System.out.println("Enrolled Courses:");
                System.out.printf("%-10s %-50s %-10s %-15s\n", "Course ID", "Course Name", "Capacity", "Type");
                System.out.println("-".repeat(80));
                for(Course course : enrolledCourses){
                    System.out.printf("%-10s %-50s %-10d %-15s\n", course.getCourseId(), course.getCourseName(), course.getCapacity(), course.getType());
                }
                System.out.println("-".repeat(80));
                Logger.logAction(studentId, "Viewed enrolled courses.");
            } 
            
            else if(studentChoice == 3){
                System.out.print("Enter Course ID to enroll: ");
                String courseId = sc.nextLine();
                Course course = CourseDAO.getAllCourses().stream()
                    .filter(c-> c.getCourseId().equals(courseId))
                    .findFirst()
                    .orElse(null);
                if(course != null){
                    Enrollment.enrollInCourse(student, course);
                    Logger.logAction(studentId, "Enrolled in course: " + courseId);
                } else {
                    System.out.println("Course not found.");
                }
            } 
            
            else if(studentChoice == 4){
                System.out.print("Enter Course ID to drop: ");
                String courseId = sc.nextLine();
                Course course = CourseDAO.getCourseById(courseId);
                if(course != null){
                    Enrollment.dropCourse(student, course);
                    Logger.logAction(studentId, "Dropped course: " + courseId);
                } else {
                    System.out.println("Course not found.");
                }
            } 
            
            else if(studentChoice == 5){
                ReportCard reportCard = new ReportCard(studentId);
                reportCard.displayReportCard();
                Logger.logAction(studentId, "Viewed report card.");
            } 
            
            else if(studentChoice == 6){
                System.out.println("Logging out...");
                Logger.logAction(studentId, "Logged out successfully");
                break;
            } 
            
            else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}

