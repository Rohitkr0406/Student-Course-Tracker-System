
public class Enrollment {
    public static void enrollInCourse(Student student){
        try(Connection conn = DBUtil.getconnection();
            Scanner sc = new Scanner(System.in)){

            //Show all available courses
            String query = "Select * from courses "
        }
    }
}