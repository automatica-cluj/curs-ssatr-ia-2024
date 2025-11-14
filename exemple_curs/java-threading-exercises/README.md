# Java Threading Exercises

A comprehensive collection of exercises demonstrating Java threading concepts and synchronization mechanisms.

## Overview

This project contains 15 progressive exercises covering essential multithreading concepts in Java, from basic thread creation to advanced synchronization patterns.

## Prerequisites

- Java 17 or higher
- Maven 3.6+

## Building the Project

```bash
cd exemple_curs/java-threading-exercises
mvn clean compile
```

## Running the Exercises

Each exercise is a standalone Java application with a `main` method. Run them individually:

```bash
# Example: Run Exercise 1
mvn exec:java -Dexec.mainClass="threading.exercises.Exercise1BasicThreadCreation"

# Or compile and run directly
javac src/main/java/threading/exercises/Exercise1BasicThreadCreation.java
java threading.exercises.Exercise1BasicThreadCreation
```

## Exercise List

### Fundamentals

#### Exercise 1: Basic Thread Creation
**File:** `Exercise1BasicThreadCreation.java`

Demonstrates three ways to create threads in Java:
- Extending `Thread` class
- Implementing `Runnable` interface
- Using lambda expressions (Java 8+)

**Key Concepts:**
- Thread instantiation
- `start()` vs `run()` methods
- `join()` method for thread coordination

---

#### Exercise 2: Thread Lifecycle
**File:** `Exercise2ThreadLifecycle.java`

Explores the different states of a thread during its lifecycle.

**Thread States:**
- NEW: Thread created but not started
- RUNNABLE: Thread executing in JVM
- BLOCKED: Thread blocked waiting for monitor lock
- WAITING: Thread waiting indefinitely
- TIMED_WAITING: Thread waiting for specified time
- TERMINATED: Thread completed execution

**Key Concepts:**
- `Thread.State` enum
- `getState()` method
- Thread priorities
- `sleep()` method

---

### Synchronization Basics

#### Exercise 3: Race Condition
**File:** `Exercise3RaceCondition.java`

Demonstrates the problem of race conditions when multiple threads access shared resources without synchronization.

**Key Concepts:**
- Race conditions
- Non-atomic operations
- Need for synchronization
- Comparing synchronized vs unsynchronized code

---

#### Exercise 4: Synchronized Methods
**File:** `Exercise4SynchronizedMethods.java`

Shows different synchronization techniques using the `synchronized` keyword.

**Key Concepts:**
- Synchronized methods
- Synchronized blocks
- Intrinsic locks (monitor locks)
- Locking on different objects
- Bank account transaction example

---

#### Exercise 5: Wait and Notify
**File:** `Exercise5WaitNotify.java`

Demonstrates inter-thread communication using `wait()`, `notify()`, and `notifyAll()`.

**Key Concepts:**
- Object-level wait/notify mechanism
- Thread coordination
- Producer-consumer communication pattern
- Importance of synchronization with wait/notify

---

### Design Patterns

#### Exercise 6: Producer-Consumer Pattern
**File:** `Exercise6ProducerConsumer.java`

Classic synchronization problem with bounded buffer.

**Key Concepts:**
- Producer-consumer pattern
- Bounded buffer implementation
- Multiple producers and consumers
- Buffer capacity management
- `notifyAll()` usage

---

### Advanced Synchronization

#### Exercise 7: ReentrantLock
**File:** `Exercise7ReentrantLock.java`

Explores `ReentrantLock` as a more flexible alternative to `synchronized`.

**Key Concepts:**
- Explicit lock acquisition/release
- `tryLock()` for non-blocking attempts
- Fair vs unfair locks
- Reentrant locking
- Lock interruptibility

---

#### Exercise 8: Semaphore
**File:** `Exercise8Semaphore.java`

Demonstrates controlling access to resources using permits.

**Key Concepts:**
- Semaphore permits
- `acquire()` and `release()`
- Fair semaphores
- Parking lot example (limiting concurrent access)
- Connection pool example

---

#### Exercise 9: CountDownLatch
**File:** `Exercise9CountDownLatch.java`

Shows how to wait for multiple operations to complete.

**Key Concepts:**
- One-time synchronization barrier
- `await()` method
- `countDown()` method
- Service startup coordination
- Coordinated thread start (race example)

---

#### Exercise 10: CyclicBarrier
**File:** `Exercise10CyclicBarrier.java`

Demonstrates reusable synchronization points for multiple threads.

**Key Concepts:**
- Reusable barrier (vs CountDownLatch)
- Barrier action
- Multi-phase computations
- Parallel algorithm synchronization
- Tourist group example

---

### Thread Pools and Executors

#### Exercise 11: Thread Pools
**File:** `Exercise11ThreadPools.java`

Explores different types of thread pools and the Executor framework.

**Thread Pool Types:**
- `FixedThreadPool`: Fixed number of threads
- `CachedThreadPool`: Dynamically sized pool
- `SingleThreadExecutor`: Single worker thread
- `ScheduledThreadPool`: Scheduled and periodic tasks

**Key Concepts:**
- ExecutorService interface
- `Callable` vs `Runnable`
- `Future` for retrieving results
- Thread pool lifecycle (`shutdown()`, `awaitTermination()`)
- Scheduled execution

---

#### Exercise 12: CompletableFuture
**File:** `Exercise12CompletableFuture.java`

Modern asynchronous programming with CompletableFuture.

**Key Concepts:**
- Asynchronous computation
- Chaining operations (`thenApply`, `thenAccept`)
- Composing dependent futures (`thenCompose`)
- Combining independent futures (`thenCombine`, `allOf`)
- Exception handling (`exceptionally`)
- Timeouts

---

### Lock-Free Programming

#### Exercise 13: Atomic Variables
**File:** `Exercise13AtomicVariables.java`

Demonstrates lock-free thread-safe operations using atomic variables.

**Atomic Types:**
- `AtomicInteger`
- `AtomicLong`
- `AtomicBoolean`
- `AtomicReference`

**Key Concepts:**
- Compare-and-swap (CAS) operations
- `compareAndSet()` method
- Lock-free algorithms
- Better performance for simple operations
- Statistics collector example

---

#### Exercise 14: Concurrent Collections
**File:** `Exercise14ConcurrentCollections.java`

Explores thread-safe collection implementations.

**Collections:**
- `ConcurrentHashMap`: Thread-safe HashMap
- `CopyOnWriteArrayList`: Read-optimized list
- `ConcurrentLinkedQueue`: Non-blocking queue
- `BlockingQueue` implementations

**Key Concepts:**
- Thread-safe collections
- Atomic operations on collections
- Blocking vs non-blocking operations
- Read-heavy vs write-heavy scenarios

---

### Problem Solving

#### Exercise 15: Deadlock
**File:** `Exercise15Deadlock.java`

Demonstrates deadlock scenarios and prevention strategies.

**Key Concepts:**
- Deadlock conditions
- Deadlock detection
- Prevention strategies:
  - Ordered lock acquisition
  - Lock timeout with `tryLock()`
  - Avoiding nested locks
  - Deadlock detection algorithms

---

## Concepts Summary

### Thread Creation
- Thread class extension
- Runnable interface
- Lambda expressions

### Synchronization Mechanisms
- synchronized keyword
- ReentrantLock
- Semaphore
- CountDownLatch
- CyclicBarrier

### Thread Communication
- wait() / notify() / notifyAll()
- BlockingQueue
- CompletableFuture

### Concurrency Utilities
- Thread pools (ExecutorService)
- Atomic variables
- Concurrent collections

### Common Problems
- Race conditions
- Deadlock
- Producer-consumer
- Thread coordination

## Best Practices

1. **Use high-level concurrency utilities** instead of low-level synchronization when possible
2. **Minimize critical sections** - hold locks for the shortest time necessary
3. **Prefer immutability** - immutable objects are inherently thread-safe
4. **Use thread pools** instead of creating threads manually
5. **Use concurrent collections** instead of synchronizing standard collections
6. **Document thread safety** - clearly indicate whether classes are thread-safe
7. **Test concurrent code** thoroughly with multiple threads and stress tests
8. **Avoid nested locks** to prevent deadlocks
9. **Use atomic variables** for simple operations instead of synchronization
10. **Handle interrupts** properly - don't ignore `InterruptedException`

## Learning Path

**Beginner:**
1. Exercise 1-2: Thread basics
2. Exercise 3-4: Basic synchronization

**Intermediate:**
3. Exercise 5-6: Thread communication
4. Exercise 7-10: Advanced synchronization
5. Exercise 11: Thread pools

**Advanced:**
6. Exercise 12: Asynchronous programming
7. Exercise 13-14: Lock-free programming
8. Exercise 15: Deadlock handling

## Common Pitfalls

1. **Forgetting to start threads** - calling `run()` instead of `start()`
2. **Race conditions** - forgetting to synchronize shared state
3. **Deadlocks** - circular wait for locks
4. **Not releasing locks** - forgetting `finally` blocks
5. **Ignoring interrupts** - catching but not handling `InterruptedException`
6. **Synchronizing on wrong object** - losing thread safety
7. **Busy waiting** - using loops instead of proper waiting mechanisms
8. **Memory visibility issues** - not using `volatile` or synchronization

## Additional Resources

- [Java Concurrency in Practice](https://jcip.net/) by Brian Goetz
- [Oracle Java Concurrency Tutorial](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- [java.util.concurrent API Documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/package-summary.html)

## Testing

Run all exercises to verify they work correctly:

```bash
mvn compile
```

Then run each exercise individually to observe the threading behavior.

## License

Educational purposes - Free to use and modify.

## Contributing

Feel free to add more exercises or improve existing ones!
