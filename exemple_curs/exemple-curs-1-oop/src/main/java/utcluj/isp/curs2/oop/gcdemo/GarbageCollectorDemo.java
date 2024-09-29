package utcluj.isp.curs2.oop.gcdemo;

import java.util.ArrayList;
import java.util.Random;

/**
 * The class has a constructor that increments the objectCount variable, and a finalize method that decrements the objectCount variable and prints a message when the object is garbage collected.
 *
 * In the main method, we first create 5 objects and print out the current value of objectCount. We then set these objects to null to make them eligible for garbage collection.
 *
 * After setting the objects to null, we manually run the garbage collector by calling the System.gc() method.
 *
 * When the garbage collector runs, it detects the objects that have been set to null and calls their finalize method before freeing up their memory.
 *
 * Finally, we print out a message to indicate that the program has completed.
 *
 * Note that while it is possible to call the garbage collector manually using the System.gc() method, it is generally not recommended as the garbage collector is designed to run automatically as needed.
 */
public class GarbageCollectorDemo {

    private static int objectCount = 0;
    private int k = 0;
    
    public GarbageCollectorDemo() {
        objectCount++;
        k = (new Random()).nextInt();
    }

    public void finalize() {
        System.out.println("Garbage collected!");
        objectCount--;
    }

    public static void main(String[] args) {
        System.out.println("Creating new objects...");
        ArrayList<GarbageCollectorDemo> list = new ArrayList<>();
        for (int i = 0; i < 500000000; i++) {
            list.add(new GarbageCollectorDemo());
            System.out.println("Created " + objectCount + " objects.");
        }

        System.out.println("Setting objects to null...");

        for (int i = 0; i < 500000000; i++) {
            GarbageCollectorDemo object = new GarbageCollectorDemo();
            System.out.println("Created " + objectCount + " objects.");
            object = null;
        }

        System.out.println("Running garbage collector...");
        System.gc();

        System.out.println("Done!");
    }
}

