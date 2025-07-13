package multithreading_concurrency.virtual.threads;

// Utility for simulating I/O delay.
public class IOUtil {
    public static void simulateIO() {
        try {
            Thread.sleep(100); // Works well with virtual threads
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

