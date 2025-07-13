package multithreading_concurrency.multithreaded.program;

/*
  🌍 Real-World Scenario: Online Food Ordering System
	🍲 Situation: You're building an app that processes two tasks simultaneously:
			🍕 Preparing the food
			🚗 Delivering the food
	These two things can happen at the same time. So, let’s run them in separate threads.
 */

/*
 	💬 Why Is This Realistic?
		In a real food delivery app:
			- The kitchen prepares food
			- The delivery guy heads out as soon as food is ready
			
		These can happen at the same time — and your program should reflect that.
 */

public class FoodOrderSystem {
	
    public static void main(String[] args) {
    	
        System.out.println("🛒 Order placed!");

        // Defining two tasks using Runnable interface.
        Runnable preparationTask = new FoodPreparation();
        Runnable deliveryTask = new FoodDelivery();

        // Create threads and wrapping the tasks inside a Thread object.
        Thread prepThread = new Thread(preparationTask);  // Wrap task with thread to tell Java what to run
        Thread deliveryThread = new Thread(deliveryTask); // Who does the task (execution unit)

        // Starts a new path of executions, and runs task.run() in that threads.
        prepThread.start();   // preparationTask runs independently
        deliveryThread.start(); // // deliveryTask runs independently
 
        System.out.println("🧾 Main thread continues while tasks are running...");
    }
}


/*
 	✅ Why Do We Wrap a Task in a Thread Object?
		Because a Thread is the actual worker that the CPU will run.
			- A Runnable is just a task — like a to-do note 📋
			- A Thread is the person doing the task — the worker 🧑‍🔧

		So we wrap the task (Runnable) inside a Thread to tell Java:
		"Here’s the task, now run it in a new thread!"
*/


/*
 	💥 Behind the scenes:
		- JVM calls the native start() method defined in the Thread class.
		- start() tells the JVM or thread scheduler to create a new execution context (a new thread stack).
		- When the new thread begins running, the JVM internally calls the thread's run() 
		  method — this is where your task starts executing.
		  
		  Note: Thread implements Runnable..
		  public class Thread implements Runnable {
		   private Runnable target;

    		public Thread(Runnable target) {
        		this.target = target;
    		}
		  	public void run() {
        		if (target != null) {
            		target.run();  // calls your task | the run() method delegates to target.run()
        		}		
    	    }
		  }
*/
