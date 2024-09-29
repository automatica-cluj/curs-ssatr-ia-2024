package utcluj.isp.curs3.interfaces1;

interface Animal {
    void eat();
    void sleep();
}

interface Bird {
    void fly();
}

class Dog implements Animal {
    @Override
    public void eat() {
        System.out.println("Dog is eating.");
    }

    @Override
    public void sleep() {
        System.out.println("Dog is sleeping.");
    }
}

class Penguin implements Animal, Bird {
    @Override
    public void eat() {
        System.out.println("Penguin is eating.");
    }

    @Override
    public void sleep() {
        System.out.println("Penguin is sleeping.");
    }

    @Override
    public void fly() {
        System.out.println("Penguin is swimming, not flying.");
    }
}

class Hawk implements Animal, Bird {
    @Override
    public void eat() {
        System.out.println("Hawk is eating.");
    }

    @Override
    public void sleep() {
        System.out.println("Hawk is sleeping.");
    }

    @Override
    public void fly() {
        System.out.println("Hawk is flying.");
    }
}

public class InterfaceDemo {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.eat();
        dog.sleep();

        Penguin penguin = new Penguin();
        penguin.eat();
        penguin.sleep();
        penguin.fly();

        Hawk hawk = new Hawk();
        hawk.eat();
        hawk.sleep();
        hawk.fly();
    }
}
