package threading.exercises;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 11: Thread Pools and ExecutorService
 *
 * Thread pools manage a pool of worker threads, allowing you to:
 * - Reuse threads instead of creating new ones
 * - Limit the number of concurrent threads
 * - Queue tasks for execution
 * - Manage thread lifecycle efficiently
 */
public class Exercise11ThreadPools {

    static class Task implements Runnable {
        private final int taskId;

        public Task(int taskId) {
            this.taskId = taskId;
        }

        @Override
        public void run() {
            System.out.println("Task " + taskId + " started by " + Thread.currentThread().getName());
            try {
                Thread.sleep((int) (Math.random() * 2000) + 500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Task " + taskId + " completed by " + Thread.currentThread().getName());
        }
    }

    static class CallableTask implements Callable<Integer> {
        private final int number;

        public CallableTask(int number) {
            this.number = number;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println("Computing square of " + number + " by " + Thread.currentThread().getName());
            Thread.sleep(1000);
            return number * number;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("=== Exercise 11: Thread Pools ===\n");

        // Part 1: Fixed Thread Pool
        System.out.println("--- Part 1: Fixed Thread Pool (3 threads, 6 tasks) ---");
        ExecutorService fixedPool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 6; i++) {
            fixedPool.execute(new Task(i));
        }

        fixedPool.shutdown();
        fixedPool.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("Fixed thread pool completed\n");

        // Part 2: Cached Thread Pool
        System.out.println("--- Part 2: Cached Thread Pool ---");
        ExecutorService cachedPool = Executors.newCachedThreadPool();

        for (int i = 1; i <= 5; i++) {
            cachedPool.execute(new Task(i));
        }

        cachedPool.shutdown();
        cachedPool.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("Cached thread pool completed\n");

        // Part 3: Single Thread Executor
        System.out.println("--- Part 3: Single Thread Executor ---");
        ExecutorService singleExecutor = Executors.newSingleThreadExecutor();

        for (int i = 1; i <= 3; i++) {
            singleExecutor.execute(new Task(i));
        }

        singleExecutor.shutdown();
        singleExecutor.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println("Single thread executor completed\n");

        // Part 4: Callable and Future
        System.out.println("--- Part 4: Callable with Future ---");
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Future<Integer>> futures = new ArrayList<>();

        // Submit callable tasks
        for (int i = 1; i <= 5; i++) {
            Future<Integer> future = executor.submit(new CallableTask(i));
            futures.add(future);
        }

        // Retrieve results
        System.out.println("\nRetrieving results:");
        for (int i = 0; i < futures.size(); i++) {
            Future<Integer> future = futures.get(i);
            Integer result = future.get(); // Blocking call
            System.out.println("Result " + (i + 1) + ": " + result);
        }

        executor.shutdown();
        System.out.println("\nCallable tasks completed\n");

        // Part 5: Scheduled Thread Pool
        System.out.println("--- Part 5: Scheduled Thread Pool ---");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // Schedule task with fixed delay
        System.out.println("Scheduling task with 1 second initial delay...");
        scheduler.schedule(() -> {
            System.out.println("Delayed task executed by " + Thread.currentThread().getName());
        }, 1, TimeUnit.SECONDS);

        // Schedule recurring task
        System.out.println("Scheduling recurring task with 1 second interval (3 times)...");
        ScheduledFuture<?> periodicTask = scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Periodic task executed at " + System.currentTimeMillis());
        }, 0, 1, TimeUnit.SECONDS);

        // Let it run for 3 seconds
        Thread.sleep(3500);
        periodicTask.cancel(false);

        scheduler.shutdown();
        scheduler.awaitTermination(2, TimeUnit.SECONDS);

        System.out.println("\nExercise completed!");
    }
}
