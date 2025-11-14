package threading.exercises;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Exercise 15: Deadlock - Detection and Prevention
 *
 * Deadlock occurs when two or more threads are blocked forever, waiting for each other.
 * This exercise demonstrates:
 * 1. How deadlocks occur
 * 2. How to prevent deadlocks
 */
public class Exercise15Deadlock {

    static class Resource {
        private final String name;

        public Resource(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    // Demonstrates deadlock scenario
    static class DeadlockExample {
        private final Resource resource1 = new Resource("Resource1");
        private final Resource resource2 = new Resource("Resource2");

        public void method1() {
            synchronized (resource1) {
                System.out.println(Thread.currentThread().getName() + " locked " + resource1.getName());

                try {
                    Thread.sleep(100); // Increase chance of deadlock
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " waiting for " + resource2.getName());
                synchronized (resource2) {
                    System.out.println(Thread.currentThread().getName() + " locked " + resource2.getName());
                    System.out.println(Thread.currentThread().getName() + " completed method1");
                }
            }
        }

        public void method2() {
            synchronized (resource2) {
                System.out.println(Thread.currentThread().getName() + " locked " + resource2.getName());

                try {
                    Thread.sleep(100); // Increase chance of deadlock
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + " waiting for " + resource1.getName());
                synchronized (resource1) {
                    System.out.println(Thread.currentThread().getName() + " locked " + resource1.getName());
                    System.out.println(Thread.currentThread().getName() + " completed method2");
                }
            }
        }
    }

    // Solution 1: Ordered lock acquisition
    static class OrderedLockingSolution {
        private final Resource resource1 = new Resource("Resource1");
        private final Resource resource2 = new Resource("Resource2");

        public void method1() {
            // Always acquire locks in the same order
            synchronized (resource1) {
                System.out.println(Thread.currentThread().getName() + " locked " + resource1.getName());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource2) {
                    System.out.println(Thread.currentThread().getName() + " locked " + resource2.getName());
                    System.out.println(Thread.currentThread().getName() + " completed method1");
                }
            }
        }

        public void method2() {
            // Same order as method1
            synchronized (resource1) {
                System.out.println(Thread.currentThread().getName() + " locked " + resource1.getName());

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource2) {
                    System.out.println(Thread.currentThread().getName() + " locked " + resource2.getName());
                    System.out.println(Thread.currentThread().getName() + " completed method2");
                }
            }
        }
    }

    // Solution 2: Using tryLock with timeout
    static class TryLockSolution {
        private final Lock lock1 = new ReentrantLock();
        private final Lock lock2 = new ReentrantLock();

        public void method1() {
            boolean lock1Acquired = false;
            boolean lock2Acquired = false;

            try {
                lock1Acquired = lock1.tryLock();
                if (lock1Acquired) {
                    System.out.println(Thread.currentThread().getName() + " acquired lock1");

                    Thread.sleep(100);

                    lock2Acquired = lock2.tryLock();
                    if (lock2Acquired) {
                        System.out.println(Thread.currentThread().getName() + " acquired lock2");
                        System.out.println(Thread.currentThread().getName() + " completed method1");
                    } else {
                        System.out.println(Thread.currentThread().getName() + " couldn't acquire lock2, releasing lock1");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock2Acquired) lock2.unlock();
                if (lock1Acquired) lock1.unlock();
            }
        }

        public void method2() {
            boolean lock1Acquired = false;
            boolean lock2Acquired = false;

            try {
                lock2Acquired = lock2.tryLock();
                if (lock2Acquired) {
                    System.out.println(Thread.currentThread().getName() + " acquired lock2");

                    Thread.sleep(100);

                    lock1Acquired = lock1.tryLock();
                    if (lock1Acquired) {
                        System.out.println(Thread.currentThread().getName() + " acquired lock1");
                        System.out.println(Thread.currentThread().getName() + " completed method2");
                    } else {
                        System.out.println(Thread.currentThread().getName() + " couldn't acquire lock1, releasing lock2");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (lock1Acquired) lock1.unlock();
                if (lock2Acquired) lock2.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Exercise 15: Deadlock ===\n");

        // Part 1: Demonstrate deadlock
        System.out.println("--- Part 1: Deadlock Example ---");
        System.out.println("WARNING: This will likely cause a deadlock!");
        System.out.println("If threads don't complete in 3 seconds, deadlock occurred.\n");

        DeadlockExample deadlock = new DeadlockExample();

        Thread t1 = new Thread(() -> deadlock.method1(), "Thread-1");
        Thread t2 = new Thread(() -> deadlock.method2(), "Thread-2");

        t1.start();
        t2.start();

        // Wait for 3 seconds to see if deadlock occurs
        t1.join(3000);
        t2.join(3000);

        if (t1.isAlive() || t2.isAlive()) {
            System.out.println("\n*** DEADLOCK DETECTED! ***");
            System.out.println("Thread-1 alive: " + t1.isAlive());
            System.out.println("Thread-2 alive: " + t2.isAlive());
            System.out.println("Interrupting threads...\n");
            t1.interrupt();
            t2.interrupt();
        } else {
            System.out.println("\nNo deadlock occurred (lucky!)\n");
        }

        Thread.sleep(500);

        // Part 2: Solution with ordered locking
        System.out.println("--- Part 2: Solution - Ordered Locking ---");
        OrderedLockingSolution orderedSolution = new OrderedLockingSolution();

        Thread t3 = new Thread(() -> orderedSolution.method1(), "Thread-3");
        Thread t4 = new Thread(() -> orderedSolution.method2(), "Thread-4");

        t3.start();
        t4.start();

        t3.join();
        t4.join();

        System.out.println("Both threads completed successfully!\n");

        // Part 3: Solution with tryLock
        System.out.println("--- Part 3: Solution - TryLock ---");
        TryLockSolution tryLockSolution = new TryLockSolution();

        Thread t5 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                tryLockSolution.method1();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-5");

        Thread t6 = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                tryLockSolution.method2();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-6");

        t5.start();
        t6.start();

        t5.join();
        t6.join();

        System.out.println("\nDeadlock Prevention Strategies:");
        System.out.println("1. Ordered Lock Acquisition - Always acquire locks in the same order");
        System.out.println("2. Lock Timeout - Use tryLock() with timeout");
        System.out.println("3. Deadlock Detection - Monitor thread states");
        System.out.println("4. Avoid Nested Locks - Minimize lock holding time");
        System.out.println("5. Use Higher-Level Concurrency Utilities");

        System.out.println("\nExercise completed!");
    }
}
