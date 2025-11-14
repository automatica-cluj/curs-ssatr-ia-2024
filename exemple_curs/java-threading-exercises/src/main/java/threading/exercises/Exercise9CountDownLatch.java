package threading.exercises;

import java.util.concurrent.CountDownLatch;

/**
 * Exercise 9: CountDownLatch
 *
 * CountDownLatch allows one or more threads to wait until a set of operations
 * being performed in other threads completes.
 *
 * Use cases:
 * - Waiting for multiple services to start before proceeding
 * - Coordinating the start of multiple threads
 * - Waiting for multiple parallel tasks to complete
 */
public class Exercise9CountDownLatch {

    static class Service implements Runnable {
        private final String serviceName;
        private final CountDownLatch latch;
        private final int startupTime;

        public Service(String serviceName, CountDownLatch latch, int startupTime) {
            this.serviceName = serviceName;
            this.latch = latch;
            this.startupTime = startupTime;
        }

        @Override
        public void run() {
            try {
                System.out.println(serviceName + " starting...");
                Thread.sleep(startupTime); // Simulate startup time
                System.out.println(serviceName + " started successfully!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown(); // Signal that this service is ready
                System.out.println(serviceName + " signaled ready. Remaining services: " + latch.getCount());
            }
        }
    }

    static class Worker implements Runnable {
        private final String workerName;
        private final CountDownLatch startLatch;
        private final CountDownLatch completionLatch;

        public Worker(String workerName, CountDownLatch startLatch, CountDownLatch completionLatch) {
            this.workerName = workerName;
            this.startLatch = startLatch;
            this.completionLatch = completionLatch;
        }

        @Override
        public void run() {
            try {
                System.out.println(workerName + " ready and waiting for start signal...");
                startLatch.await(); // Wait for start signal

                System.out.println(workerName + " started working!");
                Thread.sleep((int) (Math.random() * 2000) + 1000); // Simulate work
                System.out.println(workerName + " completed work!");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                completionLatch.countDown(); // Signal completion
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Exercise 9: CountDownLatch ===\n");

        // Part 1: Waiting for services to start
        System.out.println("--- Part 1: Application Startup (waiting for services) ---");
        final int NUM_SERVICES = 3;
        CountDownLatch serviceLatch = new CountDownLatch(NUM_SERVICES);

        // Start services
        new Thread(new Service("DatabaseService", serviceLatch, 2000)).start();
        new Thread(new Service("CacheService", serviceLatch, 1000)).start();
        new Thread(new Service("MessagingService", serviceLatch, 1500)).start();

        System.out.println("Main thread waiting for all services to start...");
        serviceLatch.await(); // Wait for all services to be ready
        System.out.println("All services started! Application is ready.\n");

        // Part 2: Coordinating parallel workers
        System.out.println("--- Part 2: Coordinated Start (race example) ---");
        final int NUM_WORKERS = 4;
        CountDownLatch startLatch = new CountDownLatch(1); // Single countdown for start signal
        CountDownLatch completionLatch = new CountDownLatch(NUM_WORKERS); // Wait for all to complete

        // Create workers
        for (int i = 1; i <= NUM_WORKERS; i++) {
            new Thread(new Worker("Worker-" + i, startLatch, completionLatch)).start();
        }

        Thread.sleep(500); // Let all workers get ready

        System.out.println("\nMain thread: Starting all workers NOW!");
        startLatch.countDown(); // Signal all workers to start

        System.out.println("Main thread waiting for all workers to complete...");
        completionLatch.await(); // Wait for all workers to finish
        System.out.println("All workers completed!\n");

        System.out.println("Exercise completed!");
    }
}
