package multithreading_concurrency.thread.lifecycle;

class TicketTask extends Thread {
    public void run() {
        System.out.println("🎫 Buying movie tickets...");
        try {
            Thread.sleep(2000); // Simulate ticketing delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("✅ Tickets bought!");
    }
}
