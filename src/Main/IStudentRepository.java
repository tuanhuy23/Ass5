package Main;

import java.util.List;

public interface IStudentRepository {
    void create(Student student) throws Exception;
    void update(Student student) throws Exception;
    Student getById(int id);
    List<Student> getAll();
    List<Student> searchByName(String name);
    void deleteById(int id);
}
