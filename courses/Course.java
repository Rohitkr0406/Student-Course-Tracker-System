package courses;

public class Course {
    private String courseId;
    private String name;
    private int capacity;
    private CourseType type;

    public Course(String courseId, String name, int capacity, CourseType type) {
        this.courseId = courseId;
        this.name = name;
        this.capacity = capacity;
        this.type = type;
    }

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public CourseType getType() { return type; }
    public void setType(CourseType type) { this.type = type; }
        

}