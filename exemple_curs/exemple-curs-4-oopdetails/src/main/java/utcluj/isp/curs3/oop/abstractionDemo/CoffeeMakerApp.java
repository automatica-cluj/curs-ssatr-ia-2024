package utcluj.isp.curs3.oop.abstractionDemo;

interface CoffeeMaker {
    void addWater(int amount);
    void addCoffee(int amount);
    void addMilk(int amount);
    void addSugar(int amount);
    void brewCoffee();
}

abstract class AbstractCoffeeMaker implements CoffeeMaker {
    private int water;
    private int coffee;
    private int milk;
    private int sugar;

    public void addWater(int amount) {
        water += amount;
    }

    public void addCoffee(int amount) {
        coffee += amount;
    }

    public void addMilk(int amount) {
        milk += amount;
    }

    public void addSugar(int amount) {
        sugar += amount;
    }

    protected abstract void brew();

    public void brewCoffee() {
        if (water >= 100 && coffee >= 10) {
            brew();
            water -= 100;
            coffee -= 10;
            if (milk > 0) {
                System.out.println("Adding milk...");
                milk = 0;
            }
            if (sugar > 0) {
                System.out.println("Adding sugar...");
                sugar = 0;
            }
            System.out.println("Your coffee is ready!");
        } else {
            System.out.println("Not enough water or coffee!");
        }
    }
}

class SimpleCoffeeMaker extends AbstractCoffeeMaker {
    protected void brew() {
        System.out.println("Brewing coffee...");
    }
}

class CappuccinoMaker extends AbstractCoffeeMaker {
    protected void brew() {
        System.out.println("Brewing espresso...");
        System.out.println("Frothing milk...");
        System.out.println("Mixing espresso and frothed milk...");
    }
}

class CoffeeMakerApp {
    public static void main(String[] args) {
        CoffeeMaker coffeeMaker = new CappuccinoMaker();
        coffeeMaker.addWater(200);
        coffeeMaker.addCoffee(20);
        coffeeMaker.addMilk(100);
        coffeeMaker.brewCoffee();
    }
}
