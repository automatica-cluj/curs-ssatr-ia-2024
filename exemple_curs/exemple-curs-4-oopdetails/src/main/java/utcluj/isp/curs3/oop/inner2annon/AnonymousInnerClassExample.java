package utcluj.isp.curs3.oop.inner2annon;

interface Greeting {
    void greet();
}

class AnonymousInnerClassExample {
    public static void main(String[] args) {
        Greeting greeting = new Greeting() {
            @Override
            public void greet() {
                System.out.println("Hello, world!");
            }
        };
        greeting.greet();

        Greeting g=() -> {
            System.out.println("Hi");
        };

        g.greet();
    }
}
