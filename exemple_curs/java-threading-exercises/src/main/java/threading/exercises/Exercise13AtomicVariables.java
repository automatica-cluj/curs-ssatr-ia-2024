package threading.exercises;

import java.util.concurrent.atomic.*;

/**
 * Exercise 13: Atomic Variables
 *
 * Atomic variables provide lock-free thread-safe operations on single variables.
 * They use low-level atomic hardware primitives (like compare-and-swap).
 *
 * Benefits:
 * - No locking overhead
 * - Better performance for simple operations
 * - Avoid deadlocks
 */
public class Exercise13AtomicVariables {

    // Compare atomic vs non-atomic counter
    static class NonAtomicCounter {
        private volatile int count = 0;

        public void increment() {
            count++; // NOT atomic!
        }

        public int getCount() {
            return count;
        }
    }

    static class AtomicCounter {
        private AtomicInteger count = new AtomicInteger(0);

        public void increment() {
            count.incrementAndGet(); // Atomic operation
        }

        public int getCount() {
            return count.get();
        }
    }

    // Demonstrate different atomic types
    static class AtomicOperations {
        private AtomicInteger atomicInt = new AtomicInteger(0);
        private AtomicLong atomicLong = new AtomicLong(0);
        private AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        private AtomicReference<String> atomicRef = new AtomicReference<>("Initial");

        public void demonstrateAtomicInt() {
            System.out.println("\n--- AtomicInteger Operations ---");
            System.out.println("Initial value: " + atomicInt.get());

            atomicInt.set(10);
            System.out.println("After set(10): " + atomicInt.get());

            int previous = atomicInt.getAndIncrement();
            System.out.println("getAndIncrement() returned: " + previous + ", new value: " + atomicInt.get());

            int current = atomicInt.incrementAndGet();
            System.out.println("incrementAndGet() returned: " + current);

            int added = atomicInt.addAndGet(5);
            System.out.println("addAndGet(5) returned: " + added);

            boolean success = atomicInt.compareAndSet(17, 100);
            System.out.println("compareAndSet(17, 100): " + success + ", value: " + atomicInt.get());
        }

        public void demonstrateAtomicBoolean() {
            System.out.println("\n--- AtomicBoolean Operations ---");
            System.out.println("Initial value: " + atomicBoolean.get());

            boolean old = atomicBoolean.getAndSet(true);
            System.out.println("getAndSet(true) returned: " + old + ", new value: " + atomicBoolean.get());

            boolean success = atomicBoolean.compareAndSet(true, false);
            System.out.println("compareAndSet(true, false): " + success + ", value: " + atomicBoolean.get());
        }

        public void demonstrateAtomicReference() {
            System.out.println("\n--- AtomicReference Operations ---");
            System.out.println("Initial value: " + atomicRef.get());

            String old = atomicRef.getAndSet("Updated");
            System.out.println("getAndSet('Updated') returned: " + old + ", new value: " + atomicRef.get());

            boolean success = atomicRef.compareAndSet("Updated", "Final");
            System.out.println("compareAndSet('Updated', 'Final'): " + success + ", value: " + atomicRef.get());
        }
    }

    // Real-world example: Statistics collector
    static class StatisticsCollector {
        private AtomicLong totalRequests = new AtomicLong(0);
        private AtomicLong successfulRequests = new AtomicLong(0);
        private AtomicLong failedRequests = new AtomicLong(0);

        public void recordSuccess() {
            totalRequests.incrementAndGet();
            successfulRequests.incrementAndGet();
        }

        public void recordFailure() {
            totalRequests.incrementAndGet();
            failedRequests.incrementAndGet();
        }

        public void printStatistics() {
            long total = totalRequests.get();
            long success = successfulRequests.get();
            long failed = failedRequests.get();

            System.out.println("\n--- Statistics ---");
            System.out.println("Total Requests: " + total);
            System.out.println("Successful: " + success + " (" + (total > 0 ? (success * 100.0 / total) : 0) + "%)");
            System.out.println("Failed: " + failed + " (" + (total > 0 ? (failed * 100.0 / total) : 0) + "%)");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Exercise 13: Atomic Variables ===\n");

        // Part 1: Compare atomic vs non-atomic
        System.out.println("--- Part 1: Atomic vs Non-Atomic Counter ---");
        final int NUM_THREADS = 10;
        final int INCREMENTS = 1000;

        NonAtomicCounter nonAtomic = new NonAtomicCounter();
        AtomicCounter atomic = new AtomicCounter();

        Thread[] threads1 = new Thread[NUM_THREADS];
        Thread[] threads2 = new Thread[NUM_THREADS];

        // Non-atomic threads
        for (int i = 0; i < NUM_THREADS; i++) {
            threads1[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    nonAtomic.increment();
                }
            });
            threads1[i].start();
        }

        // Atomic threads
        for (int i = 0; i < NUM_THREADS; i++) {
            threads2[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    atomic.increment();
                }
            });
            threads2[i].start();
        }

        for (int i = 0; i < NUM_THREADS; i++) {
            threads1[i].join();
            threads2[i].join();
        }

        System.out.println("Expected count: " + (NUM_THREADS * INCREMENTS));
        System.out.println("Non-atomic count: " + nonAtomic.getCount() + " (likely incorrect)");
        System.out.println("Atomic count: " + atomic.getCount() + " (correct)");

        // Part 2: Demonstrate different atomic operations
        System.out.println("\n--- Part 2: Atomic Operations ---");
        AtomicOperations ops = new AtomicOperations();
        ops.demonstrateAtomicInt();
        ops.demonstrateAtomicBoolean();
        ops.demonstrateAtomicReference();

        // Part 3: Real-world example
        System.out.println("\n--- Part 3: Statistics Collector ---");
        StatisticsCollector stats = new StatisticsCollector();

        Thread[] requestThreads = new Thread[20];
        for (int i = 0; i < 20; i++) {
            final int threadNum = i;
            requestThreads[i] = new Thread(() -> {
                for (int j = 0; j < 50; j++) {
                    if (Math.random() > 0.2) { // 80% success rate
                        stats.recordSuccess();
                    } else {
                        stats.recordFailure();
                    }
                }
            });
            requestThreads[i].start();
        }

        for (Thread thread : requestThreads) {
            thread.join();
        }

        stats.printStatistics();

        System.out.println("\nExercise completed!");
    }
}
