package threading.exercises;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Exercise 10: CyclicBarrier
 *
 * CyclicBarrier allows a set of threads to wait for each other to reach a common
 * barrier point. Unlike CountDownLatch, CyclicBarrier can be reused.
 *
 * Use cases:
 * - Parallel algorithms that require synchronization at certain points
 * - Multi-phase computations where each phase must complete before the next begins
 * - Simulations where multiple agents need to synchronize
 */
public class Exercise10CyclicBarrier {

    static class MatrixWorker implements Runnable {
        private final int workerId;
        private final CyclicBarrier barrier;
        private final int iterations;

        public MatrixWorker(int workerId, CyclicBarrier barrier, int iterations) {
            this.workerId = workerId;
            this.barrier = barrier;
            this.iterations = iterations;
        }

        @Override
        public void run() {
            try {
                for (int iteration = 1; iteration <= iterations; iteration++) {
                    // Phase 1: Compute
                    System.out.println("Worker-" + workerId + " computing iteration " + iteration);
                    Thread.sleep((int) (Math.random() * 1000) + 500);
                    System.out.println("Worker-" + workerId + " finished computing iteration " + iteration);

                    // Wait for all workers to finish computation
                    barrier.await();

                    // Phase 2: Synchronize (all workers reach here together)
                    System.out.println("Worker-" + workerId + " synchronized for iteration " + iteration);
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class TouristGroup implements Runnable {
        private final String touristName;
        private final CyclicBarrier barrier;
        private final String[] attractions;

        public TouristGroup(String touristName, CyclicBarrier barrier, String[] attractions) {
            this.touristName = touristName;
            this.barrier = barrier;
            this.attractions = attractions;
        }

        @Override
        public void run() {
            try {
                for (String attraction : attractions) {
                    // Visit attraction
                    System.out.println(touristName + " visiting " + attraction);
                    Thread.sleep((int) (Math.random() * 2000) + 1000);
                    System.out.println(touristName + " finished visiting " + attraction);

                    // Wait at meeting point for others
                    System.out.println(touristName + " waiting at meeting point...");
                    barrier.await();
                    System.out.println(touristName + " - everyone arrived, moving to next location!");
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Exercise 10: CyclicBarrier ===\n");

        // Part 1: Matrix computation example
        System.out.println("--- Part 1: Parallel Matrix Computation ---");
        final int NUM_WORKERS = 3;
        final int ITERATIONS = 3;

        // Barrier action runs when all threads reach the barrier
        Runnable barrierAction = () -> {
            System.out.println("*** All workers synchronized - Starting next iteration ***\n");
        };

        CyclicBarrier computationBarrier = new CyclicBarrier(NUM_WORKERS, barrierAction);

        Thread[] workers = new Thread[NUM_WORKERS];
        for (int i = 0; i < NUM_WORKERS; i++) {
            workers[i] = new Thread(new MatrixWorker(i + 1, computationBarrier, ITERATIONS));
            workers[i].start();
        }

        for (Thread worker : workers) {
            worker.join();
        }

        System.out.println("All matrix computations completed!\n");

        // Part 2: Tourist group example
        System.out.println("--- Part 2: Tourist Group Tour ---");
        final int NUM_TOURISTS = 4;
        String[] attractions = {"Museum", "Park", "Restaurant"};

        Runnable meetingAction = () -> {
            System.out.println("=== Guide: Everyone is here! Let's move to the next location ===\n");
        };

        CyclicBarrier tourBarrier = new CyclicBarrier(NUM_TOURISTS, meetingAction);

        Thread[] tourists = new Thread[NUM_TOURISTS];
        for (int i = 0; i < NUM_TOURISTS; i++) {
            tourists[i] = new Thread(new TouristGroup("Tourist-" + (i + 1), tourBarrier, attractions));
            tourists[i].start();
        }

        for (Thread tourist : tourists) {
            tourist.join();
        }

        System.out.println("Tour completed!\n");
        System.out.println("Exercise completed!");
    }
}
