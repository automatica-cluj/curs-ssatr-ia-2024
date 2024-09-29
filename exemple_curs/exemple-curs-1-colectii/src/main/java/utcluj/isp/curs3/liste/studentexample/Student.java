package utcluj.isp.curs3.liste.studentexample;

import java.util.HashMap;
import java.util.Objects;

public class Student {
    private String name;
    private String department;
    private HashMap <String, Double> grades = new HashMap<>();

    public Student(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public double calculateAvarageGrade(){
        if(grades.size()==0)
            return 0;

        int sum = 0;
        for(Double d: grades.values()){
            sum+=d;
        }
        return sum/grades.size();
    }

    public void addGrade(String clas, double grade){
        grades.put(clas,grade);
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return name.equals(student.name) && department.equals(student.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, department);
    }
}
