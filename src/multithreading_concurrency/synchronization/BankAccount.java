package multithreading_concurrency.synchronization;

public class BankAccount {
	
    private int balance = 15000;

    public synchronized void withdraw(String name, int amount) {
        if (balance >= amount) {
            System.out.println(name + " is going to withdraw ₹" + amount);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            balance -= amount;
            System.out.println(name + " completed withdrawal. Remaining balance: ₹" + balance);
        } else {
            System.out.println("Not enough balance for " + name);
        }
    }
    
    /*
    public void withdraw(String name, int amount) {
        synchronized (this) {
            if (balance >= amount) {
                System.out.println(name + " is going to withdraw ₹" + amount);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                balance -= amount;
                System.out.println(name + " completed withdrawal. Remaining balance: ₹" + balance);
            } else {
                System.out.println("Not enough balance for " + name);
            }
        }
    }
   */
}

