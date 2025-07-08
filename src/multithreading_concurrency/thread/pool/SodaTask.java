package multithreading_concurrency.thread.pool;

class SodaTask extends Thread {
    public void run() {
        System.out.println("🥤 Buying soda...");
        try {
            Thread.sleep(2000); // simulate time | Pauses current thread for given time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("✅ Soda ready!");
    }
}
