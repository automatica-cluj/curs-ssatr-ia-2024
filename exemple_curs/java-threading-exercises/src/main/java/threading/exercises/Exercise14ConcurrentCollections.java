package threading.exercises;

import java.util.*;
import java.util.concurrent.*;

/**
 * Exercise 14: Concurrent Collections
 *
 * Java provides thread-safe collections designed for concurrent access:
 * - ConcurrentHashMap: Thread-safe HashMap
 * - CopyOnWriteArrayList: Thread-safe ArrayList for read-heavy scenarios
 * - ConcurrentLinkedQueue: Non-blocking thread-safe queue
 * - BlockingQueue implementations: Thread-safe queues with blocking operations
 */
public class Exercise14ConcurrentCollections {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Exercise 14: Concurrent Collections ===\n");

        // Part 1: ConcurrentHashMap
        demonstrateConcurrentHashMap();

        // Part 2: CopyOnWriteArrayList
        demonstrateCopyOnWriteArrayList();

        // Part 3: ConcurrentLinkedQueue
        demonstrateConcurrentLinkedQueue();

        // Part 4: BlockingQueue
        demonstrateBlockingQueue();

        System.out.println("\nExercise completed!");
    }

    static void demonstrateConcurrentHashMap() throws InterruptedException {
        System.out.println("--- Part 1: ConcurrentHashMap ---");

        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

        // Multiple threads updating the map
        Thread[] threads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            final int threadNum = i;
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    String key = "key" + (j % 10);
                    // Atomic operation: compute if absent
                    map.putIfAbsent(key, 0);
                    // Atomic operation: compute new value
                    map.compute(key, (k, v) -> v == null ? 1 : v + 1);
                }
            }, "Thread-" + threadNum);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("Map size: " + map.size());
        System.out.println("Sample values:");
        map.entrySet().stream().limit(5).forEach(entry ->
                System.out.println("  " + entry.getKey() + ": " + entry.getValue())
        );

        // Demonstrate atomic operations
        System.out.println("\nAtomic operations:");
        map.put("test", 10);
        System.out.println("Initial value: " + map.get("test"));

        map.computeIfPresent("test", (k, v) -> v * 2);
        System.out.println("After computeIfPresent (v*2): " + map.get("test"));

        map.merge("test", 5, Integer::sum);
        System.out.println("After merge with 5: " + map.get("test"));

        System.out.println();
    }

    static void demonstrateCopyOnWriteArrayList() throws InterruptedException {
        System.out.println("--- Part 2: CopyOnWriteArrayList ---");
        System.out.println("Best for scenarios with many reads and few writes\n");

        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        // Writer thread
        Thread writer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                list.add("Item-" + i);
                System.out.println("Writer added: Item-" + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "Writer");

        // Reader threads
        Thread[] readers = new Thread[3];
        for (int i = 0; i < 3; i++) {
            final int readerNum = i;
            readers[i] = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println("Reader-" + readerNum + " sees " + list.size() + " items");
                    // Safe iteration even while list is being modified
                    for (String item : list) {
                        System.out.println("  Reader-" + readerNum + ": " + item);
                    }
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Reader-" + i);
            readers[i].start();
        }

        writer.start();

        writer.join();
        for (Thread reader : readers) {
            reader.join();
        }

        System.out.println("Final list: " + list);
        System.out.println();
    }

    static void demonstrateConcurrentLinkedQueue() throws InterruptedException {
        System.out.println("--- Part 3: ConcurrentLinkedQueue ---");
        System.out.println("Non-blocking thread-safe queue\n");

        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

        // Producer threads
        Thread[] producers = new Thread[2];
        for (int i = 0; i < 2; i++) {
            final int producerId = i;
            producers[i] = new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    String item = "P" + producerId + "-Item" + j;
                    queue.offer(item);
                    System.out.println("Producer-" + producerId + " added: " + item);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Producer-" + i);
            producers[i].start();
        }

        // Consumer thread
        Thread consumer = new Thread(() -> {
            int consumed = 0;
            while (consumed < 10) {
                String item = queue.poll();
                if (item != null) {
                    System.out.println("Consumer removed: " + item);
                    consumed++;
                } else {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }, "Consumer");
        consumer.start();

        for (Thread producer : producers) {
            producer.join();
        }
        consumer.join();

        System.out.println("Queue size at end: " + queue.size());
        System.out.println();
    }

    static void demonstrateBlockingQueue() throws InterruptedException {
        System.out.println("--- Part 4: BlockingQueue (ArrayBlockingQueue) ---");
        System.out.println("Thread-safe queue with blocking operations\n");

        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

        // Producer
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    System.out.println("Producer adding: " + i + " (queue size: " + queue.size() + ")");
                    queue.put(i); // Blocks if queue is full
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Producer");

        // Consumer
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    Thread.sleep(300); // Slower than producer
                    Integer item = queue.take(); // Blocks if queue is empty
                    System.out.println("Consumer took: " + item + " (queue size: " + queue.size() + ")");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Consumer");

        producer.start();
        Thread.sleep(50); // Let producer start first
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("\nDemonstrating other BlockingQueue implementations:");
        System.out.println("- LinkedBlockingQueue: Unbounded or bounded queue");
        System.out.println("- PriorityBlockingQueue: Unbounded priority queue");
        System.out.println("- SynchronousQueue: No internal capacity (direct handoff)");
        System.out.println("- DelayQueue: Elements available only after delay");
    }
}
