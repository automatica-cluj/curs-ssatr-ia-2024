package utcluj.isp.curs2.oop.robot;

import java.util.Objects;

/**
 *
 * @author mihai
 */
public class Robot7 {
    private String name;
    private int position;
    boolean state;

    public Robot7(String name, int position, boolean state) {
        this.name = name;
        this.position = position;
        this.state = state;
    }
    
    public Robot7(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
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
        final Robot7 other = (Robot7) obj;
        if (this.position != other.position) {
            return false;
        }
        if (this.state != other.state) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return "Robot7{" + "name=" + name + ", position=" + position + ", state=" + state + '}';
    }


    
    
}
