package utcluj.isp.curs3.composition;

import java.util.Objects;

class Engine {
    private int cylinders;
    private double displacement;

    public Engine(int cylinders, double displacement) {
        this.cylinders = cylinders;
        this.displacement = displacement;
    }

    public int getCylinders() {
        return cylinders;
    }

    public double getDisplacement() {
        return displacement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engine engine = (Engine) o;
        return cylinders == engine.cylinders && Double.compare(engine.displacement, displacement) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cylinders, displacement);
    }
}

class Car {
    private String make;
    private String model;
    private Engine engine;

    public Car(String make, String model, int cylinders, double displacement) {
        this.make = make;
        this.model = model;
        this.engine = new Engine(cylinders, displacement);
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Engine getEngine() {
        return engine;
    }
}

public class CompositionDemo {
    public static void main(String[] args) {
        Car car = new Car("Honda", "Accord", 4, 2.4);

        System.out.println("Make: " + car.getMake());
        System.out.println("Model: " + car.getModel());
        System.out.println("Engine: " + car.getEngine().getCylinders() + " cylinders, " + car.getEngine().getDisplacement() + " liters");
    }
}
