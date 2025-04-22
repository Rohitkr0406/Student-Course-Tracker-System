package enrollments;

import courses.Course;
import courses.CourseDAO;
import util.GradeDAO;
import java.util.List;

public class ReportCard {
    private String rollNo;
    private List<GradeDAO.EnrollmentGrade> grades;

    public ReportCard(String rollNo) {
        this.rollNo = rollNo;
        this.grades = GradeDAO.getGradesByStudentId(rollNo);
    }

    public void displayReportCard() {
        if(grades.isEmpty()) {
            System.out.println("No grades available for student ID: " + rollNo);
            return;
        }

        System.out.println("Report Card for Student ID: " + rollNo);
        System.out.printf("%-15s %-20s %-10s%n", "Course ID", "Course Name", "Grade");
        System.out.println("-------------------------------------------------");
        for(GradeDAO.EnrollmentGrade grade : grades) {
            Course course = CourseDAO.getAllCourses().stream()
                    .filter(c -> c.getCourseId().equals(grade.getCourseId()))
                    .findFirst()
                    .orElse(null);
            String courseName = (course != null) ? course.getCourseName() : "Unknown Course";
            String gradeStr = (grade.getGrade() != null) ? grade.getGrade().toString() : "N/A";
            System.out.printf("%-15s %-20s %-10s%n", grade.getCourseId(), courseName, gradeStr);
        }
        System.out.println("-------------------------------------------------");
    }
}
