package Main;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class StudentServiceStubTest {
    private StudentService service;

    @Before
    public void setUp() {
        service = new StudentService(new StubStudentRepository());
    }

    @Test
    public void testSearchStubbedData() {
        List<Student> students = service.searchStudentsByName("StubName");
        assertEquals(1, students.size());
        assertEquals("StubName", students.get(0).name);
    }
}
