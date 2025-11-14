package threading.exercises;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Exercise 7: ReentrantLock
 *
 * Demonstrates the use of ReentrantLock as an alternative to synchronized.
 * ReentrantLock provides more flexibility:
 * - Ability to try locking (tryLock)
 * - Interruptible lock acquisition
 * - Fairness policy
 * - Multiple condition variables
 */
public class Exercise7ReentrantLock {

    static class PrintQueue {
        private final Lock lock = new ReentrantLock(true); // Fair lock

        public void printDocument(String document, int pages) {
            lock.lock(); // Acquire lock
            try {
                System.out.println(Thread.currentThread().getName() + " printing: " + document);
                for (int i = 1; i <= pages; i++) {
                    System.out.println(Thread.currentThread().getName() + " - Page " + i + " of " + pages);
                    Thread.sleep(100);
                }
                System.out.println(Thread.currentThread().getName() + " finished printing: " + document);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock(); // Always unlock in finally block
            }
        }

        public void tryToPrint(String document, int pages) {
            System.out.println(Thread.currentThread().getName() + " trying to print: " + document);

            if (lock.tryLock()) { // Try to acquire lock without blocking
                try {
                    System.out.println(Thread.currentThread().getName() + " acquired lock, printing: " + document);
                    for (int i = 1; i <= pages; i++) {
                        System.out.println(Thread.currentThread().getName() + " - Page " + i + " of " + pages);
                        Thread.sleep(100);
                    }
                    System.out.println(Thread.currentThread().getName() + " finished printing: " + document);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println(Thread.currentThread().getName() + " couldn't acquire lock. Skipping: " + document);
            }
        }
    }

    static class ReentrantExample {
        private final Lock lock = new ReentrantLock();
        private int count = 0;

        public void outerMethod() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " in outerMethod");
                count++;
                innerMethod(); // Reentrant: same thread can acquire lock again
            } finally {
                lock.unlock();
            }
        }

        public void innerMethod() {
            lock.lock(); // Same thread acquiring lock again
            try {
                System.out.println(Thread.currentThread().getName() + " in innerMethod");
                count++;
            } finally {
                lock.unlock();
            }
        }

        public int getCount() {
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Exercise 7: ReentrantLock ===\n");

        // Part 1: Basic lock usage with fairness
        System.out.println("--- Part 1: Fair Lock ---");
        PrintQueue printQueue = new PrintQueue();

        Thread[] printers = new Thread[3];
        String[] documents = {"Report.pdf", "Invoice.pdf", "Contract.pdf"};

        for (int i = 0; i < 3; i++) {
            final int index = i;
            printers[i] = new Thread(() -> printQueue.printDocument(documents[index], 3), "Printer-" + (i + 1));
            printers[i].start();
        }

        for (Thread printer : printers) {
            printer.join();
        }

        // Part 2: TryLock example
        System.out.println("\n--- Part 2: TryLock ---");
        Thread t1 = new Thread(() -> printQueue.printDocument("LongDocument.pdf", 5), "Thread-1");
        Thread t2 = new Thread(() -> printQueue.tryToPrint("QuickDocument.pdf", 2), "Thread-2");

        t1.start();
        Thread.sleep(50); // Give t1 time to acquire lock
        t2.start();

        t1.join();
        t2.join();

        // Part 3: Reentrant example
        System.out.println("\n--- Part 3: Reentrant Lock ---");
        ReentrantExample reentrant = new ReentrantExample();
        Thread t3 = new Thread(() -> reentrant.outerMethod(), "ReentrantThread");
        t3.start();
        t3.join();

        System.out.println("Count after reentrant calls: " + reentrant.getCount());
        System.out.println("\nExercise completed!");
    }
}
