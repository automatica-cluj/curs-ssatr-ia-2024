package threading.exercises;

/**
 * Exercise 4: Synchronized Methods and Blocks
 *
 * Demonstrates different ways to use synchronization:
 * 1. Synchronized methods
 * 2. Synchronized blocks
 * 3. Synchronizing on different objects
 */
public class Exercise4SynchronizedMethods {

    static class BankAccount {
        private double balance;
        private final String accountNumber;

        public BankAccount(String accountNumber, double initialBalance) {
            this.accountNumber = accountNumber;
            this.balance = initialBalance;
        }

        // Synchronized method - locks on 'this' object
        public synchronized void deposit(double amount) {
            System.out.println(Thread.currentThread().getName() + " depositing " + amount);
            double newBalance = balance + amount;
            // Simulate some processing time
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            balance = newBalance;
            System.out.println(Thread.currentThread().getName() + " deposit complete. New balance: " + balance);
        }

        // Synchronized method
        public synchronized void withdraw(double amount) {
            System.out.println(Thread.currentThread().getName() + " withdrawing " + amount);
            if (balance >= amount) {
                double newBalance = balance - amount;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                balance = newBalance;
                System.out.println(Thread.currentThread().getName() + " withdrawal complete. New balance: " + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + " insufficient funds!");
            }
        }

        // Synchronized block - more granular control
        public void transfer(BankAccount destination, double amount) {
            // Lock on current account first
            synchronized (this) {
                if (balance >= amount) {
                    System.out.println(Thread.currentThread().getName() + " transferring " + amount);
                    balance -= amount;

                    // Lock on destination account
                    synchronized (destination) {
                        destination.balance += amount;
                    }
                    System.out.println(Thread.currentThread().getName() + " transfer complete");
                } else {
                    System.out.println(Thread.currentThread().getName() + " insufficient funds for transfer!");
                }
            }
        }

        public synchronized double getBalance() {
            return balance;
        }

        public String getAccountNumber() {
            return accountNumber;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Exercise 4: Synchronized Methods ===\n");

        BankAccount account1 = new BankAccount("ACC001", 1000.0);
        BankAccount account2 = new BankAccount("ACC002", 500.0);

        System.out.println("Initial balance ACC001: " + account1.getBalance());
        System.out.println("Initial balance ACC002: " + account2.getBalance());
        System.out.println();

        // Create multiple threads performing operations
        Thread t1 = new Thread(() -> account1.deposit(200), "Thread-Deposit-1");
        Thread t2 = new Thread(() -> account1.withdraw(100), "Thread-Withdraw-1");
        Thread t3 = new Thread(() -> account1.deposit(300), "Thread-Deposit-2");
        Thread t4 = new Thread(() -> account1.transfer(account2, 150), "Thread-Transfer-1");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("\nFinal balance ACC001: " + account1.getBalance());
        System.out.println("Final balance ACC002: " + account2.getBalance());
        System.out.println("Total balance: " + (account1.getBalance() + account2.getBalance()));
        System.out.println("Expected total: 1500.0");
    }
}
