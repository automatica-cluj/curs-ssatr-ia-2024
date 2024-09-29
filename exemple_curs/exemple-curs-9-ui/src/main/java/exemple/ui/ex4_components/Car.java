package exemple.ui.ex4_components;

public class Car {

    private int id;
    private String model;
    public Car(int i, String dacia_logan) {
        this.id = i;
        this.model = dacia_logan;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }
}
