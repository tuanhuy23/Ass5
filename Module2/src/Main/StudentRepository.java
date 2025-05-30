package Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentRepository implements IStudentRepository {
    private final Connection conn;

    public StudentRepository(Connection conn) {
        this.conn = conn;
    }

    private static String getRating(float score) {
        if (score >= 8.0) return "Excellent";
        if (score >= 5.0) return "Fair";
        return "Average";
    }

    public void create(Student student) throws Exception {
        if (getById(student.id) != null) {
            throw new Exception("Student ID already exists");
        }
        String sql = "INSERT INTO Students (id, name, dob, gender, averageScore, rating) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, student.id);
            stmt.setString(2, student.name);
            stmt.setDate(3, new java.sql.Date(student.dob.getTime()));
            stmt.setBoolean(4, student.gender);
            stmt.setFloat(5, student.averageScore);
            stmt.setString(6, student.rating);
            stmt.executeUpdate();
        }
    }

    public void update(Student student) throws Exception {
        if (getById(student.id) == null) {
            throw new Exception("Student not found");
        }
        String sql = "UPDATE Students SET name=?, dob=?, gender=?, averageScore=?, rating=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, student.name);
            stmt.setDate(2, new java.sql.Date(student.dob.getTime()));
            stmt.setBoolean(3, student.gender);
            stmt.setFloat(4, student.averageScore);
            stmt.setString(5, student.rating);
            stmt.setInt(6, student.id);
            stmt.executeUpdate();
        }
    }

    public Student getById(int id) {
        String sql = "SELECT * FROM Students WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("dob"),
                        rs.getBoolean("gender"),
                        rs.getFloat("averageScore"),
                        rs.getString("rating")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("dob"),
                        rs.getBoolean("gender"),
                        rs.getFloat("averageScore"),
                        rs.getString("rating")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Student> searchByName(String name) {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM Students WHERE LOWER(name) = LOWER(?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("dob"),
                        rs.getBoolean("gender"),
                        rs.getFloat("averageScore"),
                        rs.getString("rating")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteById(int id)  {
        String sql = "DELETE FROM Students WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
