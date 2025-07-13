package multithreading_concurrency.virtual.threads;

// Represents a single booking task.
import java.util.concurrent.Callable;

public class BookingTask implements Callable<String> {
    private final int bookingId;

    public BookingTask(int bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public String call() {
        IOUtil.simulateIO();
        return "✅ Booking #" + bookingId + " confirmed by " + Thread.currentThread();
    }
}

