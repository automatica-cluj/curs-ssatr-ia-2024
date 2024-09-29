package utcluj.isp.curs3.liste.studentexample;

import java.util.ArrayList;
public class GradesManagerSystem {
    private ArrayList<Student> list = new ArrayList<>();
    public void addStudent(Student s){
        list.add(s);
    }
    void printAll(){
        for(Student s:list) System.out.println(s);
    }

    public Student searchByName(String name){
        for(Student s: list)
            if(s.getName().equals(name))
                return s;

        return null;
    }

    public void remove(String name){
        Student x = searchByName(name);
        if(x!=null)
            list.remove(x);
    }

    public void remove(Student s){
        list.remove(s);
    }

}
