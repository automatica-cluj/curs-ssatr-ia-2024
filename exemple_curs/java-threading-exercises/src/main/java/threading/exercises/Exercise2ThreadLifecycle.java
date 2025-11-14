package threading.exercises;

/**
 * Exercise 2: Thread Lifecycle and States
 *
 * Demonstrates different states of a thread:
 * - NEW: Thread is created but not started
 * - RUNNABLE: Thread is executing
 * - BLOCKED: Thread is blocked waiting for a monitor lock
 * - WAITING: Thread is waiting indefinitely for another thread
 * - TIMED_WAITING: Thread is waiting for another thread for a specified time
 * - TERMINATED: Thread has completed execution
 */
public class Exercise2ThreadLifecycle {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Exercise 2: Thread Lifecycle ===\n");

        // Create a thread - NEW state
        Thread thread = new Thread(() -> {
            System.out.println("Thread is running...");

            try {
                // TIMED_WAITING state
                System.out.println("Thread entering TIMED_WAITING state...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Thread finishing execution...");
        });

        // NEW state
        System.out.println("Thread state after creation: " + thread.getState());

        // Start the thread - RUNNABLE state
        thread.start();
        Thread.sleep(100); // Give thread time to start
        System.out.println("Thread state after start: " + thread.getState());

        // Wait a bit and check if it's in TIMED_WAITING
        Thread.sleep(500);
        System.out.println("Thread state during sleep: " + thread.getState());

        // Wait for thread to complete
        thread.join();

        // TERMINATED state
        System.out.println("Thread state after completion: " + thread.getState());

        // Demonstrate thread priorities
        System.out.println("\n--- Thread Priorities ---");
        Thread highPriority = new Thread(() -> {
            System.out.println("High priority thread executing");
        });
        Thread lowPriority = new Thread(() -> {
            System.out.println("Low priority thread executing");
        });

        highPriority.setPriority(Thread.MAX_PRIORITY);
        lowPriority.setPriority(Thread.MIN_PRIORITY);

        System.out.println("High priority: " + highPriority.getPriority());
        System.out.println("Low priority: " + lowPriority.getPriority());

        lowPriority.start();
        highPriority.start();

        highPriority.join();
        lowPriority.join();

        System.out.println("\nExercise completed!");
    }
}
