package multithreading_concurrency.virtual.threads;

// Submits and processes bookings.
import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class BookingService {
	
    private final ExecutorService executor;
    private final List<Future<String>> futures = new ArrayList<>();

    private final List<String> confirmedBookings = new ArrayList<>(); // Shared Resource
    private final ReentrantLock lock = new ReentrantLock();

    public BookingService(ExecutorService executor) {
        this.executor = executor;
    }

    // Submitting totalBookings number of virtual threads, each responsible for processing a booking.
    public void submitBookings(int totalBookings) {
        for (int i = 1; i <= totalBookings; i++) {
            int bookingId = i;

            // Defining virtual thread’s task
            // ✅ Each task is isolated and thread-safe.
            Callable<String> task = () -> {
                BookingTask bookingTask = new BookingTask(bookingId);
                String result = bookingTask.call();

                // Multiple virtual threads will run this code concurrently.
                // 🔒 Concurrent write to shared list | 🔒 Thread-Safe List Update
                // Adds the result to a shared list, safely, using a lock.
                lock.lock();
                try {
                     // Protecting add() operation using a ReentrantLock to prevent data corruption or race conditions.
                    confirmedBookings.add(result); 
                } finally {
                    lock.unlock();
                }
                return result;
            };
            // Submitting the defined task to the virtual thread executor.
            // All task results are stored in futures, so they can be awaited later.
            futures.add(executor.submit(task));
        }
    }

    
    public int waitForAllBookings(int printLimit) {
        int confirmed = 0;

        for (Future<String> future : futures) {
            try {
                String result = future.get();
                if (confirmed < printLimit) {
                    System.out.println(result);
                }
                confirmed++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return confirmed;
    }

    public List<String> getConfirmedBookings() {
        return confirmedBookings;
    }
}

