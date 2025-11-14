# Java Threading Quick Reference

## Thread Creation

```java
// Method 1: Extend Thread
class MyThread extends Thread {
    public void run() { /* work */ }
}
new MyThread().start();

// Method 2: Implement Runnable
class MyTask implements Runnable {
    public void run() { /* work */ }
}
new Thread(new MyTask()).start();

// Method 3: Lambda
new Thread(() -> { /* work */ }).start();
```

## Synchronization

### synchronized keyword
```java
// Synchronized method
public synchronized void method() { }

// Synchronized block
synchronized(object) { /* critical section */ }
```

### ReentrantLock
```java
Lock lock = new ReentrantLock();
lock.lock();
try {
    // critical section
} finally {
    lock.unlock();
}

// Try lock
if (lock.tryLock()) {
    try { /* work */ }
    finally { lock.unlock(); }
}
```

### Semaphore
```java
Semaphore semaphore = new Semaphore(3); // 3 permits
semaphore.acquire();
try {
    // limited access section
} finally {
    semaphore.release();
}
```

## Thread Communication

### wait/notify
```java
synchronized(obj) {
    while (!condition) {
        obj.wait();
    }
    // proceed
}

synchronized(obj) {
    // change condition
    obj.notify(); // or notifyAll()
}
```

### CountDownLatch
```java
CountDownLatch latch = new CountDownLatch(3);

// Worker threads
latch.countDown();

// Main thread
latch.await(); // wait for all
```

### CyclicBarrier
```java
CyclicBarrier barrier = new CyclicBarrier(3);

// Each thread
barrier.await(); // wait for others
```

## Thread Pools

```java
// Fixed thread pool
ExecutorService executor = Executors.newFixedThreadPool(5);

// Submit tasks
executor.execute(() -> { /* work */ });
Future<Result> future = executor.submit(() -> { return result; });

// Shutdown
executor.shutdown();
executor.awaitTermination(1, TimeUnit.MINUTES);
```

## CompletableFuture

```java
// Async execution
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    return "result";
});

// Chaining
future.thenApply(result -> transform(result))
      .thenAccept(result -> process(result))
      .exceptionally(ex -> handleError(ex));

// Combining
CompletableFuture.allOf(future1, future2, future3).get();
```

## Atomic Variables

```java
AtomicInteger counter = new AtomicInteger(0);

counter.incrementAndGet();
counter.addAndGet(5);
counter.compareAndSet(expected, newValue);

// Other types
AtomicLong, AtomicBoolean, AtomicReference
```

## Concurrent Collections

```java
// Map
ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();
map.putIfAbsent(key, value);
map.compute(key, (k, v) -> newValue);

// List (read-heavy)
CopyOnWriteArrayList<E> list = new CopyOnWriteArrayList<>();

// Queue
ConcurrentLinkedQueue<E> queue = new ConcurrentLinkedQueue<>();
queue.offer(element);
E element = queue.poll();

// Blocking queue
BlockingQueue<E> queue = new ArrayBlockingQueue<>(capacity);
queue.put(element); // blocks if full
E element = queue.take(); // blocks if empty
```

## Common Patterns

### Producer-Consumer
```java
BlockingQueue<Task> queue = new ArrayBlockingQueue<>(100);

// Producer
queue.put(task);

// Consumer
Task task = queue.take();
```

### Read-Write Lock
```java
ReadWriteLock rwLock = new ReentrantReadWriteLock();

// Read
rwLock.readLock().lock();
try { /* read */ }
finally { rwLock.readLock().unlock(); }

// Write
rwLock.writeLock().lock();
try { /* write */ }
finally { rwLock.writeLock().unlock(); }
```

## Thread Safety Rules

1. **Stateless objects** are always thread-safe
2. **Immutable objects** are always thread-safe
3. **Synchronize access** to mutable shared state
4. **Use thread-safe collections** instead of synchronizing
5. **Minimize lock scope** - hold locks briefly
6. **Never call alien methods** while holding a lock
7. **Use volatile** for simple flags
8. **Document thread safety** of your classes

## Performance Tips

1. Use **thread pools** instead of creating threads
2. Use **concurrent collections** for better scalability
3. Use **atomic variables** for counters
4. Prefer **lock-free algorithms** when possible
5. Use **read-write locks** for read-heavy scenarios
6. **Cache thread locals** for expensive per-thread objects
7. **Batch operations** to reduce synchronization overhead

## Debugging Tips

1. Use **jstack** to get thread dumps
2. Enable **deadlock detection** in monitoring tools
3. Use **ThreadMXBean** for programmatic monitoring
4. Add **logging** with thread names
5. Use **Thread.currentThread().getName()** in logs
6. Test with **different thread counts**
7. Use **stress tests** to expose race conditions

## Thread States

```
NEW → RUNNABLE ⇄ BLOCKED/WAITING/TIMED_WAITING → TERMINATED
```

- **NEW**: Created but not started
- **RUNNABLE**: Executing
- **BLOCKED**: Waiting for lock
- **WAITING**: Waiting indefinitely (wait(), join())
- **TIMED_WAITING**: Waiting with timeout (sleep(), wait(timeout))
- **TERMINATED**: Completed

## InterruptedException Handling

```java
try {
    Thread.sleep(1000);
} catch (InterruptedException e) {
    // Option 1: Restore interrupt status
    Thread.currentThread().interrupt();

    // Option 2: Propagate
    throw new RuntimeException(e);

    // Option 3: Handle and continue
    // Clean up and return
}
```

## Memory Visibility

### volatile
```java
private volatile boolean flag = false;
```
- Guarantees visibility across threads
- No atomicity for compound operations
- Use for simple flags

### happens-before
- Starting a thread happens-before any action in that thread
- Unlocking happens-before locking the same lock
- Writing to volatile happens-before reading it
- Thread termination happens-before join() returns

## Common Mistakes

❌ Calling `run()` instead of `start()`
❌ Not synchronizing shared mutable state
❌ Synchronizing on wrong object
❌ Nested locks in different order (deadlock)
❌ Forgetting to unlock in finally block
❌ Ignoring InterruptedException
❌ Using synchronized collections in compound operations
❌ Not using volatile for flags

## When to Use What

| Use Case | Solution |
|----------|----------|
| Simple counter | AtomicInteger |
| Complex state | synchronized or ReentrantLock |
| Producer-consumer | BlockingQueue |
| Wait for multiple tasks | CountDownLatch or CompletableFuture.allOf |
| Repeated sync points | CyclicBarrier |
| Limited resources | Semaphore |
| Async pipeline | CompletableFuture |
| Thread-safe map | ConcurrentHashMap |
| Read-heavy list | CopyOnWriteArrayList |
| Task execution | ExecutorService |
