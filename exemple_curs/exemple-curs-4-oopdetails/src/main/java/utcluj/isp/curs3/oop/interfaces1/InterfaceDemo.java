package utcluj.isp.curs3.oop.interfaces1;

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

    static void tryFly(Bird b){
        b.fly();
    }
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.eat();
        dog.sleep();

        Penguin penguin = new Penguin();
        penguin.eat();
        penguin.sleep();
        penguin.fly();

        Animal a1 = new Penguin();
        a1.eat();
        ((Bird)a1).fly();
        Bird b1 = (Bird)a1;
        Penguin p1 = (Penguin)a1;
        tryFly((Bird)a1);

        Hawk hawk = new Hawk();
        hawk.eat();
        hawk.sleep();
        hawk.fly();
    }
}
