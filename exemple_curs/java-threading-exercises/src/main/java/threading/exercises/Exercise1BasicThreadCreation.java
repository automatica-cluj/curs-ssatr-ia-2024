package threading.exercises;

/**
 * Exercise 1: Basic Thread Creation
 *
 * Demonstrates three ways to create and run threads in Java:
 * 1. Extending Thread class
 * 2. Implementing Runnable interface
 * 3. Using lambda expressions (Java 8+)
 */
public class Exercise1BasicThreadCreation {

    // Method 1: Extending Thread class
    static class MyThread extends Thread {
        private String threadName;

        public MyThread(String name) {
            this.threadName = name;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                System.out.println(threadName + " - Count: " + i);
                try {
                    Thread.sleep(500); // Sleep for 500ms
                } catch (InterruptedException e) {
                    System.out.println(threadName + " interrupted.");
                }
            }
            System.out.println(threadName + " finished.");
        }
    }

    // Method 2: Implementing Runnable interface
    static class MyRunnable implements Runnable {
        private String taskName;

        public MyRunnable(String name) {
            this.taskName = name;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                System.out.println(taskName + " - Count: " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(taskName + " interrupted.");
                }
            }
            System.out.println(taskName + " finished.");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Exercise 1: Basic Thread Creation ===\n");

        // Method 1: Creating thread by extending Thread class
        System.out.println("Method 1: Extending Thread class");
        MyThread thread1 = new MyThread("Thread-1");
        thread1.start();

        // Method 2: Creating thread using Runnable interface
        System.out.println("Method 2: Implementing Runnable");
        Thread thread2 = new Thread(new MyRunnable("Task-1"));
        thread2.start();

        // Method 3: Using lambda expression (Java 8+)
        System.out.println("Method 3: Using Lambda\n");
        Thread thread3 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Lambda-Thread - Count: " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Lambda-Thread interrupted.");
                }
            }
            System.out.println("Lambda-Thread finished.");
        });
        thread3.start();

        // Wait for all threads to complete
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nAll threads completed!");
    }
}
