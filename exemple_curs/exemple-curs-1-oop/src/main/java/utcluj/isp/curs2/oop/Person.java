package utcluj.isp.curs2.oop;

/**
 * Principle of encapsulation - getters and setters.
 */
public class Person {

    private String name;
    private int age;
    private String address;

    public Person(String name, int age, String address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    //tostring method
    public String toString() {
        return "Name: " + name + "\n"
                + "Age: " + age + "\n"
                + "Address: " + address + "\n";
    }

    public void printInfo() {
        System.out.println(toString());
    }

    public static void main(String[] args) {
        Person person = new Person("John Doe", 30, "123 Main Street");
        System.out.println(person);
//        person.printInfo();
//        person.setAge(31);
//        person.setAddress("456 High Street");
//        person.printInfo();
    }
}
