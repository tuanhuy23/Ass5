package Main;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

public class StudentServiceTest {
    private static StudentService service;
    private static StudentRepository repo;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static Connection conn;

    @BeforeClass
    public static void setupOnce() throws Exception {
        Class.forName("org.h2.Driver");
        conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE Students (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    name VARCHAR(100) NOT NULL,\n" +
                "    dob DATE NOT NULL,\n" +
                "    gender BOOLEAN NOT NULL,\n" +
                "    averageScore FLOAT NOT NULL CHECK (averageScore >= 0.0 AND averageScore <= 10.0),\n" +
                "    rating VARCHAR(10) NOT NULL CHECK (rating IN ('Excellent', 'Fair', 'Average'))\n" +
                ")");
        repo = new StudentRepository(conn);
        service = new StudentService(repo);
    }

    @AfterClass
    public static void tearDownOnce() throws Exception {
        conn.close();
    }

    @Before
    public void resetData() throws Exception {
        Statement stmt = conn.createStatement();
        stmt.execute("DELETE FROM Students");
    }

    @Test
    public void testCreateStudentSuccess() throws Exception {
        Student s = new Student(100, "New Student", sdf.parse("2002-02-02"), true, 9.0f, "Excellent");
        service.createStudent(s);
        assertEquals("New Student", repo.getById(100).name);
        repo.deleteById(100);
    }

    @Test(expected = Exception.class)
    public void testCreateStudentWithExistingId() throws Exception {
        Student s = new Student(1, "Duplicate", sdf.parse("2001-01-01"), true, 6.0f, "Fair");
        service.createStudent(s);
    }

    @Test
    public void testUpdateStudentSuccess() throws Exception {
        Student s = new Student(1, "Updated Name", sdf.parse("2001-01-01"), false, 7.5f, "Fair");
        service.updateStudent(s);
        assertEquals("Updated Name", repo.getById(1).name);
    }

    @Test(expected = Exception.class)
    public void testUpdateNonExistingStudent() throws Exception {
        Student s = new Student(999, "Ghost", sdf.parse("2001-01-01"), true, 4.0f, "Average");
        service.updateStudent(s);
    }

    @Test
    public void testGetAllStudents() {
        List<Student> all = service.getAllStudents();
        assertEquals(10, all.size());
    }

    @Test
    public void testSearchStudentByNameFound() throws Exception {
        Student s = new Student(200, "John", sdf.parse("2000-10-10"), true, 8.5f, "Excellent");
        repo.create(s);
        List<Student> result = service.searchStudentsByName("John");
        assertEquals(1, result.size());
        repo.deleteById(200);
    }

    @Test
    public void testSearchStudentByNameNotFound() {
        List<Student> result = service.searchStudentsByName("NonExist");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testDeleteStudentById() throws Exception {
        Student s = new Student(300, "Temp", sdf.parse("2002-03-03"), false, 5.5f, "Fair");
        repo.create(s);
        service.deleteStudentById(300);
        assertNull(repo.getById(300));
    }
}
