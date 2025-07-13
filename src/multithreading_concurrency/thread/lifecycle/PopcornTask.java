package multithreading_concurrency.thread.lifecycle;

class PopcornTask extends Thread {
	
    public void run() {
        System.out.println("🍿 Buying popcorn...");
        try {
            Thread.sleep(3000); // simulate time | Pauses current thread for given time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("✅ Popcorn ready!");
    }
}
