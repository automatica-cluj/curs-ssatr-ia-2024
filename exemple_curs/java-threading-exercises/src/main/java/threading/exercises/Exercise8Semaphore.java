package threading.exercises;

import java.util.concurrent.Semaphore;

/**
 * Exercise 8: Semaphore
 *
 * Semaphore is used to control access to a shared resource through the use of permits.
 * Common use cases:
 * - Limiting the number of concurrent accesses to a resource
 * - Implementing connection pools
 * - Rate limiting
 */
public class Exercise8Semaphore {

    static class ParkingLot {
        private final Semaphore semaphore;
        private final int totalSpaces;

        public ParkingLot(int spaces) {
            this.totalSpaces = spaces;
            this.semaphore = new Semaphore(spaces, true); // Fair semaphore
        }

        public void parkCar(String carName) {
            try {
                System.out.println(carName + " trying to park...");
                semaphore.acquire(); // Wait for available space
                System.out.println(carName + " parked. Available spaces: " + semaphore.availablePermits());

                // Simulate parking time
                Thread.sleep((int) (Math.random() * 3000) + 1000);

                System.out.println(carName + " leaving...");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                semaphore.release(); // Release the space
                System.out.println(carName + " left. Available spaces: " + semaphore.availablePermits());
            }
        }
    }

    static class ConnectionPool {
        private final Semaphore connections;
        private final int maxConnections;

        public ConnectionPool(int maxConnections) {
            this.maxConnections = maxConnections;
            this.connections = new Semaphore(maxConnections);
        }

        public boolean tryGetConnection(String clientName) {
            try {
                System.out.println(clientName + " requesting connection...");

                if (connections.tryAcquire()) {
                    System.out.println(clientName + " got connection. Available: " + connections.availablePermits());
                    return true;
                } else {
                    System.out.println(clientName + " no connections available!");
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public void releaseConnection(String clientName) {
            connections.release();
            System.out.println(clientName + " released connection. Available: " + connections.availablePermits());
        }

        public void useConnection(String clientName) {
            try {
                if (tryGetConnection(clientName)) {
                    // Simulate using connection
                    Thread.sleep((int) (Math.random() * 2000) + 500);
                    releaseConnection(clientName);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Exercise 8: Semaphore ===\n");

        // Part 1: Parking lot example
        System.out.println("--- Part 1: Parking Lot (3 spaces, 6 cars) ---");
        ParkingLot parkingLot = new ParkingLot(3);

        Thread[] cars = new Thread[6];
        for (int i = 0; i < 6; i++) {
            final String carName = "Car-" + (i + 1);
            cars[i] = new Thread(() -> parkingLot.parkCar(carName));
            cars[i].start();
        }

        for (Thread car : cars) {
            car.join();
        }

        // Part 2: Connection pool example
        System.out.println("\n--- Part 2: Connection Pool (2 connections, 5 clients) ---");
        ConnectionPool pool = new ConnectionPool(2);

        Thread[] clients = new Thread[5];
        for (int i = 0; i < 5; i++) {
            final String clientName = "Client-" + (i + 1);
            clients[i] = new Thread(() -> pool.useConnection(clientName));
            clients[i].start();
        }

        for (Thread client : clients) {
            client.join();
        }

        System.out.println("\nExercise completed!");
    }
}
