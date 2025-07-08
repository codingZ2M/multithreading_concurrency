package multithreading_concurrency.multithreaded.program;

//Task 1: Preparing food
// Runnable: What to do (task description)
class FoodPreparation implements Runnable {
	
 public void run() {
	 
     System.out.println("🍳 Food preparation started by: " + Thread.currentThread().getName());
     try {
         Thread.sleep(3000); // Simulate time to cook
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
     System.out.println("✅ Food is ready!");
 }
 
}