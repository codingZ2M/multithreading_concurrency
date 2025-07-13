package multithreading_concurrency.synchronization;

/*
   Scenario: Two people (threads) trying to withdraw money from the same bank account (shared resource).

	Problem without synchronization:
		Both users try to withdraw ₹10,000.
		Balance is ₹15,000.

	Without synchronization, both threads might pass the balance check and cause overdraft.
 */

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();

        Runnable r1 = () -> account.withdraw("Alice", 10000);
        Runnable r2 = () -> account.withdraw("Bob", 10000);

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        t2.start();
    }
}
