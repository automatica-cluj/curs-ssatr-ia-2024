package utcluj.isp.curs2.oop.robot;

/**
 * Comparing objects.
 */
public class Robot5 {

    private int name;
    private int position;
    private final int STEP_SIZE = 2;

    public Robot5(int name, int position) {
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

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Robot5 other = (Robot5) obj;
        return this.name == other.name;
    }

   

    public static void main(String[] args) {
        Robot5 robot1 = new Robot5(1234, 0);
        Robot5 robot2 = new Robot5(5678, 0);
        Robot5 robot3 = new Robot5(1234, 0);

        System.out.println("robot1 == robot2: " + robot1.equals(robot2));
        System.out.println("robot1 == robot3: " + robot1.equals(robot3));
        
        
        if(robot1.equals(robot3))
            System.out.println("OK");
        else
            System.out.println("NOK");
    
        String s1="Un sir de caractere";
        String s2= new String("Un sir de caractere");
        
        System.out.println(s1.equals(s2));
        System.out.println(robot1.equals(robot3));
        
        
    }
}
