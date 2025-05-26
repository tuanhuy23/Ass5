package Main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryStudentRepository implements IStudentRepository {
    private final Map<Integer, Student> db = new HashMap<>();

    public InMemoryStudentRepository() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 1; i <= 10; i++) {
                db.put(i, new Student(i, "Student" + i, sdf.parse("2000-01-0" + (i % 9 + 1)), i % 2 == 0, (float) (i % 11), getRating((float) (i % 11))));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getRating(float score) {
        if (score >= 8.0) return "Excellent";
        if (score >= 5.0) return "Fair";
        return "Average";
    }

    public void create(Student student) throws Exception {
        if (db.containsKey(student.id)) {
            throw new Exception("Student with ID already exists");
        }
        db.put(student.id, student);
    }

    public void update(Student student) throws Exception {
        if (!db.containsKey(student.id)) {
            throw new Exception("Student not found");
        }
        db.put(student.id, student);
    }

    public Student getById(int id) {
        return db.get(id);
    }

    public List<Student> getAll() {
        return new ArrayList<>(db.values());
    }

    public List<Student> searchByName(String name) {
        return db.values().stream()
                .filter(s -> s.name.equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public void deleteById(int id) {
        db.remove(id);
    }
}
