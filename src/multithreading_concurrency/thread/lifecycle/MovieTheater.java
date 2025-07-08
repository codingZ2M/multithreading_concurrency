package multithreading_concurrency.thread.lifecycle;

/*
  🎯 Real-World Scenario: Movie Theater
  Imagine this: You (main thread) want to watch a movie.
		🎫 Buy movie tickets (must finish first)
		🍿 Buy popcorn
		🥤 Buy soda
		🎥 Start the movie — only after all above tasks are done

  We’ll model:
	Tickets: must finish before starting snacks
	Snacks: can be done in parallel
	Use join() to control the flow
	
	🎯 What Does join() Do?
		join() makes the main thread wait until other threads complete.
 */

/*
 	🎁 Optional: Want to See What Happens Without join()?
		Remove the two join() calls. You might get this:
		🎬 Entering theater...
		🍿 Buying popcorn...
		🥤 Buying soda...
		🎫 Buy movie tickets...
		🎥 Starting movie now that snacks are ready!
		✅ Soda ready!
		✅ Popcorn ready!
		❌ The movie starts before the snacks are ready! That’s why we need join().
 */

public class MovieTheater {	
    public static void main(String[] args) {
        System.out.println("🎬 Entering theater...");
        
        // Step 1: Buy tickets
        Thread ticketThread = new TicketTask();
        ticketThread.start();
        
        try {
            ticketThread.join();  // 🍿🥤 wait until 🎫 is done
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Step 2: Buy snacks (in parallel), after tickets | Snacks prep can run simultaneously
        Thread popcornThread = new PopcornTask();
        Thread sodaThread = new SodaTask();

        // Starting two independent threads...
        popcornThread.start();  // starts a new thread and runs run() in it
        sodaThread.start();

        // 🎯 Wait for both threads to finish
        try {
            popcornThread.join();   // Makes the main thread wait until popcorn is ready
            sodaThread.join();     // Makes the main thread wait until soda is ready
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("🎥 Starting movie now that snacks are ready!");
    }
}


/*
 	🧠 What Did You Learn?
	🪄 Thread Lifecycle Stages
	Lifecycle	NEW → RUNNABLE → RUNNING → WAITING/TIMED_WAITING → TERMINATED
	
	| State                          | What it means                                             |
| ------------------------------ | --------------------------------------------------------- |
| **NEW**                        | Thread is created, not started yet                        |
| **RUNNABLE**                   | Thread has been started, ready to run                     |
| **RUNNING**                    | Thread is actually executing `run()`                      |
| **BLOCKED**                    | Thread is waiting for a lock                              |
| ** WAITING/TIMED_WAITING **	 | Thread is sleeping or waiting (e.g., sleep(), join())	 |
| ** TERMINATED**				 | Task is done — thread is dead
 */
