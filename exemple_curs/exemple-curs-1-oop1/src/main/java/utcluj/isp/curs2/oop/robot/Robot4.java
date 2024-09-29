package utcluj.isp.curs2.oop.robot;

/**
 * COncept of encapsulations getters & setters.
 */
public class Robot4 {

    private int name;
    private int position;
    private final int STEP_SIZE = 2;

    public Robot4(int name, int position) {
        this.name = name;
        this.position = position;
    }

    public void move(int distance) {
        position += distance;
        System.out.println("Robot moved to position " + position);
    }

    public void move() {
        position += STEP_SIZE;
        System.out.println("Robot moved to position " + position);
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    
    
    
    public static void main(String[] args) {
        Robot4 robot = new Robot4(1234, 0);
        System.out.println("Initial robot name: " + robot.getName());
        System.out.println("Initial robot position: " + robot.getPosition());

        robot.move(10);
        System.out.println("Robot position after move(10): " + robot.getPosition());

        robot.move();
        System.out.println("Robot position after move(): " + robot.getPosition());

        robot.setName(5678);
        System.out.println("Robot name after setName(5678): " + robot.getName());

        robot.setPosition(20);
        System.out.println("Robot position after setPosition(20): " + robot.getPosition());
    }
}

