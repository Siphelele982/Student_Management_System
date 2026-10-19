package student_management_system;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    
    public void addStudent(String name, int age, String course) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO students (name, age, course) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, course);
            stmt.executeUpdate();
            System.out.println("Student added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }

    public List<StudentEntity> getStudents() {
        List<StudentEntity> students = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
        String sql = "SELECT * FROM students";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            StudentEntity s = new StudentEntity(
            rs.getInt("studentId"),
            rs.getString("name"),
            rs.getInt("age"),
            rs.getString("course"));
            
            students.add(s);
        }
        }
         
        catch (SQLException e) {
        e.printStackTrace();
    }
        return students;
    }
    public List<StudentEntity> getStudentsByFirstLetter(char firstLetter) {
        List<StudentEntity> students = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM students WHERE name LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, firstLetter + "%"); 
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                StudentEntity s = new StudentEntity(
                    rs.getInt("studentId"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("course"));
            students.add(s);
        }
        }
        catch (SQLException e) {
            e.printStackTrace();
    }
        return students;
    }
    public void updateStudent(int studentId, String name, int age, String course) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE students SET name=?, age=?, course=? WHERE studentId=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, course);
            stmt.setInt(4, studentId);
            stmt.executeUpdate();
            System.out.println("Student updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteStudent(int studentId) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM students WHERE studentId=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);
            stmt.executeUpdate();
            System.out.println("Student deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
