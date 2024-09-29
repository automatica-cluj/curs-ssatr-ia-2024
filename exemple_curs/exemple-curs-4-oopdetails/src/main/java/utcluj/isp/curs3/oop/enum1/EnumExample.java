package utcluj.isp.curs3.oop.enum1;

enum Gender {
    MALE,
    FEMALE
}

class Person {
    private String name;
    private int age;
    private Gender gender;

    public Person(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}

public class EnumExample {
    public static void main(String[] args) {
        Person person1 = new Person("John", 30, Gender.MALE);
        Person person2 = new Person("Jane", 25, Gender.FEMALE);

        System.out.println(person1.getName() + " is a " + person1.getGender() + " and is " + person1.getAge() + " years old.");
        System.out.println(person2.getName() + " is a " + person2.getGender() + " and is " + person2.getAge() + " years old.");
    }
}
