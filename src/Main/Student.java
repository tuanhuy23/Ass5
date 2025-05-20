package Main;

import java.util.Date;

public class Student {
    public int id;
    public String name;
    public Date dob;
    public boolean gender;
    public float averageScore;
    public String rating;

    public Student(int id, String name, Date dob, boolean gender, float averageScore, String rating) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.averageScore = averageScore;
        this.rating = rating;
    }
}
