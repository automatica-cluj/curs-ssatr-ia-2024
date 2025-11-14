package threading.exercises;

/**
 * Exercise 5: Wait and Notify Mechanism
 *
 * Demonstrates inter-thread communication using wait() and notify()
 * This is the foundation for implementing producer-consumer patterns.
 */
public class Exercise5WaitNotify {

    static class Message {
        private String content;
        private boolean hasMessage = false;

        // Called by reader thread
        public synchronized String read() {
            while (!hasMessage) {
                try {
                    System.out.println(Thread.currentThread().getName() + " waiting for message...");
                    wait(); // Wait until a message is available
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            hasMessage = false;
            System.out.println(Thread.currentThread().getName() + " read message: " + content);
            notify(); // Notify writer that message has been read
            return content;
        }

        // Called by writer thread
        public synchronized void write(String content) {
            while (hasMessage) {
                try {
                    System.out.println(Thread.currentThread().getName() + " waiting to write...");
                    wait(); // Wait until previous message is read
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.content = content;
            hasMessage = true;
            System.out.println(Thread.currentThread().getName() + " wrote message: " + content);
            notify(); // Notify reader that message is available
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Exercise 5: Wait and Notify ===\n");

        Message message = new Message();

        // Writer thread
        Thread writer = new Thread(() -> {
            String[] messages = {"Hello", "How are you?", "Goodbye"};
            for (String msg : messages) {
                message.write(msg);
                try {
                    Thread.sleep(500); // Simulate time between writes
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Writer");

        // Reader thread
        Thread reader = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                message.read();
                try {
                    Thread.sleep(1000); // Simulate processing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Reader");

        writer.start();
        reader.start();

        try {
            writer.join();
            reader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nExercise completed!");
    }
}
