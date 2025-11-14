package threading.exercises;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Exercise 12: CompletableFuture
 *
 * CompletableFuture provides a powerful way to write asynchronous, non-blocking code.
 * It supports:
 * - Chaining operations
 * - Combining multiple futures
 * - Exception handling
 * - Async execution
 */
public class Exercise12CompletableFuture {

    // Simulate API calls
    static CompletableFuture<String> fetchUserData(int userId) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching user " + userId + " by " + Thread.currentThread().getName());
            sleep(1000);
            return "User" + userId;
        });
    }

    static CompletableFuture<String> fetchUserOrders(String userName) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching orders for " + userName + " by " + Thread.currentThread().getName());
            sleep(1000);
            return userName + "'s orders: [Order1, Order2]";
        });
    }

    static CompletableFuture<Double> calculateDiscount(String orders) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Calculating discount by " + Thread.currentThread().getName());
            sleep(500);
            return 10.5;
        });
    }

    static CompletableFuture<String> fetchProductInfo(int productId) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching product " + productId + " by " + Thread.currentThread().getName());
            sleep((int) (Math.random() * 1000) + 500);
            return "Product" + productId;
        });
    }

    static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("=== Exercise 12: CompletableFuture ===\n");

        // Part 1: Simple async operation
        System.out.println("--- Part 1: Simple Async Operation ---");
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Computing by " + Thread.currentThread().getName());
            sleep(1000);
            return "Hello, CompletableFuture!";
        });

        System.out.println("Main thread continues...");
        String result1 = future1.get(); // Blocking
        System.out.println("Result: " + result1 + "\n");

        // Part 2: Chaining operations (thenApply, thenAccept)
        System.out.println("--- Part 2: Chaining Operations ---");
        CompletableFuture<Void> chain = CompletableFuture.supplyAsync(() -> {
            System.out.println("Step 1: Starting computation by " + Thread.currentThread().getName());
            sleep(500);
            return 10;
        }).thenApply(result -> {
            System.out.println("Step 2: Multiplying by 2 by " + Thread.currentThread().getName());
            sleep(500);
            return result * 2;
        }).thenApply(result -> {
            System.out.println("Step 3: Adding 5 by " + Thread.currentThread().getName());
            sleep(500);
            return result + 5;
        }).thenAccept(result -> {
            System.out.println("Final result: " + result);
        });

        chain.get();
        System.out.println();

        // Part 3: Composing dependent futures (thenCompose)
        System.out.println("--- Part 3: Composing Dependent Futures ---");
        CompletableFuture<String> userOrdersFuture = fetchUserData(123)
                .thenCompose(user -> fetchUserOrders(user))
                .thenCompose(orders -> calculateDiscount(orders)
                        .thenApply(discount -> orders + ", Discount: " + discount));

        System.out.println("Result: " + userOrdersFuture.get() + "\n");

        // Part 4: Combining independent futures (thenCombine)
        System.out.println("--- Part 4: Combining Independent Futures ---");
        CompletableFuture<String> product1 = fetchProductInfo(1);
        CompletableFuture<String> product2 = fetchProductInfo(2);

        CompletableFuture<String> combined = product1.thenCombine(product2,
                (p1, p2) -> "Combined: " + p1 + " and " + p2);

        System.out.println("Result: " + combined.get() + "\n");

        // Part 5: Waiting for all futures (allOf)
        System.out.println("--- Part 5: Waiting for Multiple Futures ---");
        CompletableFuture<String> f1 = fetchProductInfo(10);
        CompletableFuture<String> f2 = fetchProductInfo(20);
        CompletableFuture<String> f3 = fetchProductInfo(30);

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(f1, f2, f3);
        allFutures.get();

        System.out.println("All futures completed:");
        System.out.println("  " + f1.get());
        System.out.println("  " + f2.get());
        System.out.println("  " + f3.get());
        System.out.println();

        // Part 6: Exception handling
        System.out.println("--- Part 6: Exception Handling ---");
        CompletableFuture<String> futureWithError = CompletableFuture.supplyAsync(() -> {
            System.out.println("Processing by " + Thread.currentThread().getName());
            if (Math.random() > 0.5) {
                throw new RuntimeException("Random error occurred!");
            }
            return "Success";
        }).exceptionally(ex -> {
            System.out.println("Handling exception: " + ex.getMessage());
            return "Recovered from error";
        });

        System.out.println("Result: " + futureWithError.get() + "\n");

        // Part 7: Timeout
        System.out.println("--- Part 7: Timeout ---");
        try {
            CompletableFuture<String> slowOperation = CompletableFuture.supplyAsync(() -> {
                sleep(3000);
                return "Completed";
            });

            String result = slowOperation.get(1, TimeUnit.SECONDS);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Operation timed out: " + e.getClass().getSimpleName());
        }

        System.out.println("\nExercise completed!");
    }
}
