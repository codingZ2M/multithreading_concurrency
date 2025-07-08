package multithreading_concurrency.virtual.threads;

import java.util.concurrent.*;
import java.util.*;

public class HotelBookingWithVirtualThreads {
    public static void main(String[] args) {
        int totalBookings = 10_000;

        // Create virtual thread executor (Java 21)
        // ExecutorService: Java's standard interface for managing threads
        // ExecutorService, Spins up one new virtual thread per submitted task
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        List<Future<String>> futures = new ArrayList<>();

        System.out.println("📞 Receiving bookings...");

        // Submit 10,000 Callable<String> tasks
        for (int i = 1; i <= totalBookings; i++) {
            int bookingId = i;

            Callable<String> bookingTask = () -> {
                simulateIO(); // Simulate network/DB delay
                return "✅ Booking #" + bookingId + " confirmed by " + Thread.currentThread();
            };

            Future<String> future = executor.submit(bookingTask); // Submit Callable
            futures.add(future);
        }

        System.out.println("⏳ Processing all bookings...");

        int confirmed = 0;
        for (Future<String> future : futures) {
            try {
                String result = future.get();
                if (confirmed < 50) // Just show 50 tasks
                    System.out.println(result);
                confirmed++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("🎉 All " + confirmed + " bookings completed!");

        executor.shutdown();
    }

    // Simulate I/O
    private static void simulateIO() {
        try {
            Thread.sleep(100); // Non-blocking on virtual threads
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
