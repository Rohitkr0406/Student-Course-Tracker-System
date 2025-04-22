package users;

public class Student extends User {

    private int age;
    private String contact;

    public Student(String rollno, String name, int age, String contact, String password) {
        super(rollno, name, password);
        this.age = age;
        this.contact = contact;
    }

    @Override
    public String getrole() {
        return "Student";
    }


    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    
}