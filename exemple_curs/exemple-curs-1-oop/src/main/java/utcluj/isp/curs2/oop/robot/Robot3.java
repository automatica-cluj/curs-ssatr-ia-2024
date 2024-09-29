package utcluj.isp.curs2.oop.robot;

/**
 * Demonstrates of methods overloading.
 */
public class Robot3 {

    private int name;
    private int position;

    public Robot3(int name, int position) {
        this.name = name;
        this.position = position;
    }

    public void move(int distance) {
        position += distance;
        System.out.println("Robot moved to position " + position);
    }

    public void move() {
        position++;
        System.out.println("Robot moved to position " + position);
    }

    public static void main(String[] args) {
        Robot3 robot = new Robot3(1234, 0);
        robot.move(10);
        robot.move();
        robot.move(-5);
        robot.move();
    }
}
