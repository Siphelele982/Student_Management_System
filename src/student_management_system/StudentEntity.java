package student_management_system;


public class StudentEntity {
    private int studentId;
    private String name;
    private int age;
    private String course;

    public StudentEntity(int studentId, String name, int age, String course) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public int getStudentId() { 
        return studentId; 
    }
    public String getName() {
        return name; 
    }
    public int getAge() {
        return age; 
    }
    public String getCourse() { 
        return course; 
    }
}

