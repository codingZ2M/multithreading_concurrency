package multithreading_concurrency.thread.pool;

import java.util.concurrent.*;

/*
   ⚠️ Problem with Manual Threads:
		-Threads are expensive (in memory & time)
		-Creating many threads manually is hard to manage
		-You can’t reuse them easily
		-You lose control over how threads are scheduled
 */

/*
   ✅ Solution: Use a Thread Pool via Executors
		In Java (including Java 21), Executors help manage a pool of reusable threads.
		Instead of creating new threads each time, you submit tasks to a pool and it decides:
		    -When to run them?
			-Which thread to use (existing or new)
			-How to queue tasks efficiently
 */

public class MovieTheaterWithExecutor {
    public static void main(String[] args) {
        System.out.println("🎬 Entering theater...");

        // ✅ Create a thread pool with 3 reusable threads
        // Java will manage those 3 threads efficiently (reusing them instead of creating new ones for every task).
        /* Instead of manually creating and managing threads (new Thread()), you submit tasks to an
            ExecutorService, and it manages and controls thread execution. 
         */
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // 🎫 Buy tickets first |  Submitting task to the executor to run it on a thread from the thread pool.
        // The executor takes that task (not a thread!)
        Future<?> ticketFuture = executor.submit(
        		new Runnable() {
        		    public void run() {
        		        System.out.println("🎫 Buying movie tickets...");
        		        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
        		        System.out.println("✅ Tickets bought!");
        		    }
        		}
        	);

        // ⏳ Wait for ticket task to finish before snacks
        try {
        	// 🔒 Blocks only the thread that calls it — typically the main thread, if you're calling it from main().
            ticketFuture.get(); // Blocks until ticket is done, Waits for the result of a submitted task
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // 🍿 and 🥤 can run in parallel | Submitting task to the executor to run it on a thread from the thread pool.
        // The executor takes that task (not a thread!)
        Future<?> popcornFuture = executor.submit(() -> {
            System.out.println("🍿 Buying popcorn...");
            try { Thread.sleep(3000); } catch (InterruptedException ignored) {}
            System.out.println("✅ Popcorn ready!");
        });

        Future<?> sodaFuture = executor.submit(() -> {
            System.out.println("🥤 Buying soda...");
            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
            System.out.println("✅ Soda ready!");
        });

        // ⏳ Wait for both snacks to finish
        try {
            popcornFuture.get(); // Blocks until popcorn is done, Waits for the result of a submitted task
            sodaFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("🎥 Starting movie now that everything is ready!");

        // ✅ Always shut down the executor when done
        executor.shutdown();
    }
}

/*
   🔍 Real-World Analogy:
		Think of a ExecutorService as a task manager with a team of 3 workers:
		You give it work (submit()),
		It assigns workers (threads) automatically,
		You don't worry about hiring/firing (thread creation/destruction),
		You can shut down the whole team cleanly (shutdown()).
 */


/*
 	🧠 Simple Analogy
 	Imagine:
		You order food (submit the task).
		The kitchen (executor) starts cooking.
		You wait at the counter (get()) until it’s ready.
	You don't do the cooking when you wait — you just wait for the food to be ready.
	
	✅ Summary
	| Method Call          | What It Does                                                                                |
| -------------------- | ------------------------------------------------------------------------------------------- |
| `executor.submit()`  | Submits the `Runnable` to run **in background**                                             |
| `run()`              | Executed by a **worker thread**, not by `.get()`                                            |
| `ticketFuture.get()` | **Waits** for the task to complete and then returns `null` (since `Runnable` has no return) |

 */
