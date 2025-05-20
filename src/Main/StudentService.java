package Main;

import java.util.List;

public class StudentService {
    private final IStudentRepository repo;

    public StudentService(IStudentRepository repo) {
        this.repo = repo;
    }

    public void createStudent(Student student) throws Exception {
        repo.create(student);
    }

    public void updateStudent(Student student) throws Exception {
        repo.update(student);
    }

    public List<Student> getAllStudents() {
        return repo.getAll();
    }

    public List<Student> searchStudentsByName(String name) {
        return repo.searchByName(name);
    }

    public void deleteStudentById(int id) {
        repo.deleteById(id);
    }
}
