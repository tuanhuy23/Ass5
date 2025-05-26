package Main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StubStudentRepository  implements IStudentRepository{
    public void create(Student student) {}
    public void update(Student student) {}
    public Student getById(int id) { return null; }
    public List<Student> getAll() { return null; }
    public List<Student> searchByName(String name) {
        if ("StubName".equalsIgnoreCase(name)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return Arrays.asList(new Student(1, "StubName", sdf.parse("2000-01-01"), true, 9.0f, "Excellent"));
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }
    public void deleteById(int id) {}
}
