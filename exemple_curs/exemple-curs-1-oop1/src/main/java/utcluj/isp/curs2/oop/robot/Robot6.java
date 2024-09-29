package utcluj.isp.curs2.oop.robot;

import lombok.*;

/**
 * Demonstrate usage of lombok library to generate boilerplate code.
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Robot6 {

    private int name;
    private int position;
    private final int STEP_SIZE = 2;

    public void move(int distance) {
        position += distance;
        System.out.println("Robot moved to position " + position);
    }

    public void move() {
        position += STEP_SIZE;
        System.out.println("Robot moved to position " + position);
    }

    public static void main(String[] args) {
        Robot6 robot1 = new Robot6(1234, 0);
        Robot6 robot2 = new Robot6(5678, 0);
        Robot6 robot3 = new Robot6(1234, 0);

        System.out.println("robot1 == robot2: " + robot1.equals(robot2));
        System.out.println("robot1 == robot3: " + robot1.equals(robot3));
    }
}
