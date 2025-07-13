package multithreading_concurrency.thread.pool;

import java.util.concurrent.*;

public class MovieTheaterWithExecutorCallable {
	
    public static void main(String[] args) {
        System.out.println("🎬 Entering theater...");

        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 🎫 Buy tickets using Callable
        Callable<String> buyTicketsTask = () -> {
            System.out.println("🎫 Buying movie tickets...");
            Thread.sleep(2000);
            return "✅ Tickets bought!";
        };

        Future<String> ticketFuture = executor.submit(buyTicketsTask);

        // ⏳ Wait for ticket before snacks
        try {
            String ticketResult = ticketFuture.get();
            System.out.println(ticketResult);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // 🍿 and 🥤 can run in parallel using Callable
        Callable<String> buyPopcornTask = () -> {
            System.out.println("🍿 Buying popcorn...");
            Thread.sleep(3000);
            return "✅ Popcorn ready!";
        };

        Callable<String> buySodaTask = () -> {
            System.out.println("🥤 Buying soda...");
            Thread.sleep(2000);
            return "✅ Soda ready!";
        };

        Future<String> popcornFuture = executor.submit(buyPopcornTask);
        Future<String> sodaFuture = executor.submit(buySodaTask);

        try {
            String popcornResult = popcornFuture.get();
            String sodaResult = sodaFuture.get();
            System.out.println(popcornResult);
            System.out.println(sodaResult);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("🎥 Starting movie now that everything is ready!");
        executor.shutdown();
    }
}

