package utcluj.isp.curs3.oop.interface5default;

/**
 * Examples from https://www.baeldung.com/java-static-default-methods 
 */
interface Vehicle {

    String getBrand();
    String speedUp();
    String slowDown();

    default String turnAlarmOn() {
        return "Turning the vehicle alarm on.";
    }

    default String turnAlarmOff() {
        return "Turning the vehicle alarm off.";
    }
}

class Car implements Vehicle {

    private String brand;

    public Car(String brand) {
        this.brand = brand;
    }

    // constructors/getters

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String speedUp() {
        return "The car is speeding up.";
    }

    @Override
    public String slowDown() {
        return "The car is slowing down.";
    }
}

public class CarDemo {
    public static void main(String[] args) {
        Vehicle car = new Car("BMW");
        System.out.println(car.getBrand());
        System.out.println(car.speedUp());
        System.out.println(car.slowDown());
        System.out.println(car.turnAlarmOn());
        System.out.println(car.turnAlarmOff());
    }
}
