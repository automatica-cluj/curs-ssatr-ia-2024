package utcluj.isp.curs3.oop.interfaces3;

/**
 * This example demonstrate usage of interfaces and enums. Also it exemplify factory pattern.
 */
interface Vehicle {
    void start();
    void stop();
}

class Car implements Vehicle {
    @Override
    public void start() {
        System.out.println("Starting the car...");
    }

    @Override
    public void stop() {
        System.out.println("Stopping the car...");
    }
}

class Bike implements Vehicle {
    @Override
    public void start() {
        System.out.println("Starting the bike...");
    }

    @Override
    public void stop() {
        System.out.println("Stopping the bike...");
    }
}

class VehicleFactory {
    public enum VehicleType {
        CAR,
        BIKE
    }

    public static Vehicle createVehicle(VehicleType type) {
        switch (type) {
            case CAR:
                return new Car();
            case BIKE:
                return new Bike();
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
}

public class EnumExample {
    public static void main(String[] args) {
        Vehicle car = VehicleFactory.createVehicle(VehicleFactory.VehicleType.CAR);
        Vehicle bike = VehicleFactory.createVehicle(VehicleFactory.VehicleType.BIKE);

        car.start();
        car.stop();
        bike.start();
        bike.stop();
    }
}
