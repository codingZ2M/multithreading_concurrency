package multithreading_concurrency.virtual.threads;

//  Manages the virtual thread executor.
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BookingExecutor {
	
    private final ExecutorService executor;

    public BookingExecutor() {
    	// Create virtual thread executor (Java 21)
        // ExecutorService: Java's standard interface for managing threads
        // ExecutorService, Spins up one new virtual thread per submitted task | Creates the virtual thread executor.
        this.executor = Executors.newVirtualThreadPerTaskExecutor();
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void shutdown() {
        executor.shutdown();
    }
}

