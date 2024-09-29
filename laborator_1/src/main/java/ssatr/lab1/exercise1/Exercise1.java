package ssatr.lab1.exercise1;

public class Exercise1 {

    public static void main(String[] args) {
        Address a1 = new Address("asdas", "123");
        System.out.println(a1);
        
        Customer c1 = new Customer(new Address("aaa","bb"), "id1");
        
        System.out.println(c1);
        
    }
}
