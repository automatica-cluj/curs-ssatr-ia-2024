package utcluj.isp.curs3.liste.maps;

import java.util.HashMap;
import java.util.Map;

public class StudentsGradesExample {
    public static void main(String[] args) {
        // Create a new HashMap
        Map<String, Double> studentGrades = new HashMap<>();

        // Add grades for each class
        studentGrades.put("Math", 90.0);
        studentGrades.put("Science", 80.0);
        studentGrades.put("English", 95.0);

        // Calculate average grade
        double sum = 0.0;
        for (Double grade : studentGrades.values()) {
            sum += grade;
        }
        double averageGrade = sum / studentGrades.size();

        // Print average grade
        System.out.println("Average grade: " + averageGrade);
    }
}
