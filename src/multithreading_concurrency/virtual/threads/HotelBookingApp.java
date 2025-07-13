package multithreading_concurrency.virtual.threads;

// Main application entry point.
/*
 Using virtual threads to concurrently modify a shared resource with proper locking. You’ve got a real-world 
 concurrent programming example using modern Java 21+ constructs.
 */
public class HotelBookingApp {
	
    public static void main(String[] args) {
    	
        int totalBookings = 10_000;
        int printLimit = 1000;

        BookingExecutor bookingExecutor = new BookingExecutor();
        
        BookingService bookingService = new BookingService(bookingExecutor.getExecutor());

        System.out.println("📞 Receiving bookings...");
        bookingService.submitBookings(totalBookings);

        System.out.println("⏳ Processing all bookings...");
        int confirmed = bookingService.waitForAllBookings(printLimit);

        System.out.println("🎉 All " + confirmed + " bookings completed!");

        bookingExecutor.shutdown();
    }
}
