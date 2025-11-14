package threading.exercises;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Exercise 6: Producer-Consumer Pattern
 *
 * Classic synchronization problem where:
 * - Producers generate data and put it in a buffer
 * - Consumers take data from the buffer and process it
 * - Buffer has limited capacity
 */
public class Exercise6ProducerConsumer {

    static class SharedBuffer<T> {
        private final Queue<T> queue = new LinkedList<>();
        private final int capacity;

        public SharedBuffer(int capacity) {
            this.capacity = capacity;
        }

        // Producer calls this method
        public synchronized void produce(T item) throws InterruptedException {
            while (queue.size() == capacity) {
                System.out.println(Thread.currentThread().getName() + " - Buffer full, waiting...");
                wait(); // Wait if buffer is full
            }

            queue.add(item);
            System.out.println(Thread.currentThread().getName() + " produced: " + item + " (Buffer size: " + queue.size() + ")");

            notifyAll(); // Notify consumers that item is available
        }

        // Consumer calls this method
        public synchronized T consume() throws InterruptedException {
            while (queue.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " - Buffer empty, waiting...");
                wait(); // Wait if buffer is empty
            }

            T item = queue.poll();
            System.out.println(Thread.currentThread().getName() + " consumed: " + item + " (Buffer size: " + queue.size() + ")");

            notifyAll(); // Notify producers that space is available

            return item;
        }

        public synchronized int size() {
            return queue.size();
        }
    }

    static class Producer implements Runnable {
        private final SharedBuffer<Integer> buffer;
        private final int itemsToProduce;

        public Producer(SharedBuffer<Integer> buffer, int itemsToProduce) {
            this.buffer = buffer;
            this.itemsToProduce = itemsToProduce;
        }

        @Override
        public void run() {
            try {
                for (int i = 1; i <= itemsToProduce; i++) {
                    buffer.produce(i);
                    Thread.sleep((int) (Math.random() * 1000)); // Random delay
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Consumer implements Runnable {
        private final SharedBuffer<Integer> buffer;
        private final int itemsToConsume;

        public Consumer(SharedBuffer<Integer> buffer, int itemsToConsume) {
            this.buffer = buffer;
            this.itemsToConsume = itemsToConsume;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i < itemsToConsume; i++) {
                    Integer item = buffer.consume();
                    // Simulate processing
                    Thread.sleep((int) (Math.random() * 1500));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Exercise 6: Producer-Consumer Pattern ===\n");

        final int BUFFER_CAPACITY = 5;
        final int ITEMS_PER_PRODUCER = 10;
        final int NUM_PRODUCERS = 2;
        final int NUM_CONSUMERS = 2;

        SharedBuffer<Integer> buffer = new SharedBuffer<>(BUFFER_CAPACITY);

        // Create producers
        Thread[] producers = new Thread[NUM_PRODUCERS];
        for (int i = 0; i < NUM_PRODUCERS; i++) {
            producers[i] = new Thread(new Producer(buffer, ITEMS_PER_PRODUCER), "Producer-" + (i + 1));
            producers[i].start();
        }

        // Create consumers
        Thread[] consumers = new Thread[NUM_CONSUMERS];
        int itemsPerConsumer = (ITEMS_PER_PRODUCER * NUM_PRODUCERS) / NUM_CONSUMERS;
        for (int i = 0; i < NUM_CONSUMERS; i++) {
            consumers[i] = new Thread(new Consumer(buffer, itemsPerConsumer), "Consumer-" + (i + 1));
            consumers[i].start();
        }

        // Wait for all threads to complete
        for (Thread producer : producers) {
            producer.join();
        }
        for (Thread consumer : consumers) {
            consumer.join();
        }

        System.out.println("\nAll production and consumption completed!");
        System.out.println("Final buffer size: " + buffer.size());
    }
}
