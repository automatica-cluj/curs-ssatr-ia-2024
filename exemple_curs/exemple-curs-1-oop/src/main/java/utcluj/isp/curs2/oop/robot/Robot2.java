package utcluj.isp.curs2.oop.robot;

/**
 * Class with a constructor, attributes and methods.
 */
public class Robot2 {

    private int name;
    private int position;

    public Robot2(int name, int position) {
        this.name = name;
        this.position = position;
    }

    public void move(int distance) {
        position += distance;
        System.out.println("Robot moved to position " + position);
    }

    public static void main(String[] args) {
        Robot2 robot = new Robot2(1234, 0);
        robot.move(10);
        robot.move(-5);
        
        Robot2 robot2 = new Robot2(5678, 0);
        robot2.move(10);

        System.out.println(robot);
        System.out.println(robot2);
        
        Robot2 robot3=null;
        System.out.println(robot3);
        
        
        Robot2 r3=robot;
        r3.move(22);
        
        robot2 = robot;
    }
}
