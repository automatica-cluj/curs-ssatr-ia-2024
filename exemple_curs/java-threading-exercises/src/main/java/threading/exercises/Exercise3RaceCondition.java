package threading.exercises;

/**
 * Exercise 3: Race Condition Problem
 *
 * Demonstrates what happens when multiple threads access shared resources
 * without proper synchronization.
 */
public class Exercise3RaceCondition {

    // Shared counter - not thread-safe
    static class UnsafeCounter {
        private int count = 0;

        public void increment() {
            count++; // This is NOT atomic!
        }

        public int getCount() {
            return count;
        }
    }

    // Thread-safe counter using synchronized keyword
    static class SafeCounter {
        private int count = 0;

        public synchronized void increment() {
            count++;
        }

        public synchronized int getCount() {
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Exercise 3: Race Condition ===\n");

        // Demonstrate race condition
        System.out.println("--- Demonstrating Race Condition ---");
        UnsafeCounter unsafeCounter = new UnsafeCounter();
        demonstrateCounter(unsafeCounter, "Unsafe");

        System.out.println("\n--- Demonstrating Thread-Safe Counter ---");
        SafeCounter safeCounter = new SafeCounter();
        demonstrateCounter(safeCounter, "Safe");
    }

    private static void demonstrateCounter(Object counter, String type) throws InterruptedException {
        final int NUM_THREADS = 10;
        final int INCREMENTS_PER_THREAD = 1000;

        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS_PER_THREAD; j++) {
                    if (counter instanceof UnsafeCounter) {
                        ((UnsafeCounter) counter).increment();
                    } else {
                        ((SafeCounter) counter).increment();
                    }
                }
            });
            threads[i].start();
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }

        int finalCount;
        if (counter instanceof UnsafeCounter) {
            finalCount = ((UnsafeCounter) counter).getCount();
        } else {
            finalCount = ((SafeCounter) counter).getCount();
        }

        System.out.println(type + " Counter - Expected: " + (NUM_THREADS * INCREMENTS_PER_THREAD));
        System.out.println(type + " Counter - Actual: " + finalCount);
        System.out.println(type + " Counter - Match: " + (finalCount == NUM_THREADS * INCREMENTS_PER_THREAD));
    }
}
