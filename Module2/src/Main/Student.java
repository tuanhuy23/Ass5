package Main;

import java.util.Date;

public class Student {
    public int id;
    public String name;
    public Date dob;
    public boolean gender;
    public float averageScore;
    public String rating;

    public Student(int id, String name, Date dob, boolean gender, float averageScore, String rating) throws Exception {
        if (dob.after(new Date())) {
            throw new Exception("Date of birth cannot be in the future");
        }
        if (!rating.equals("Excellent") && !rating.equals("Fair") && !rating.equals("Average")) {
            throw new Exception("Rating must be 'Excellent', 'Fair', or 'Average'");
        }
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.averageScore = averageScore;
        this.rating = rating;
    }
}
