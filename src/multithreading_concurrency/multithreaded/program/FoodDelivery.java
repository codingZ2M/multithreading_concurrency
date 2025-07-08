package multithreading_concurrency.multithreaded.program;

//Task 2: Delivering food
// Runnable: What to do (task description)
class FoodDelivery implements Runnable { 
	
 public void run() {
     System.out.println("🚚 Delivery started by: " + Thread.currentThread().getName());
     try {
         Thread.sleep(5000); // Simulate delivery time
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
     System.out.println("🏠 Food delivered to customer!");
 }
 
}
