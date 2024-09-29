package utcluj.isp.curs3.aggregation;

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
}

class Gearbox {
    private int gears;

    public Gearbox(int gears) {
        this.gears = gears;
    }

    public int getGears() {
        return gears;
    }
}

class Car {
    private String make;
    private String model;
    private Engine engine;
    private Gearbox gearbox;

    public Car(String make, String model, Engine engine, Gearbox gearbox) {
        this.make = make;
        this.model = model;
        this.engine = engine;
        this.gearbox = gearbox;
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

    public Gearbox getGearbox() {
        return gearbox;
    }
}

public class AggregationDemo {
    public static void main(String[] args) {
        Engine engine = new Engine(4, 2.4);
        Gearbox gearbox = new Gearbox(6);
        Car car = new Car("Honda", "Accord", engine, gearbox);

        System.out.println("Make: " + car.getMake());
        System.out.println("Model: " + car.getModel());
        System.out.println("Engine: " + car.getEngine().getCylinders() + " cylinders, " + car.getEngine().getDisplacement() + " liters");
        System.out.println("Gearbox: " + car.getGearbox().getGears() + " gears");
    }
}
