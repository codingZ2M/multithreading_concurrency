package multithreading_concurrency.reentrantlock;

// 🏦 Let's Build a Realistic ATM Simulation
/*
  We’ll simulate:
	Multiple users (threads) trying to access different ATM operations
	A shared bank account balance (our shared resource 💰)
	Use of:
		lock.lock() 🔒
		lock.tryLock() 🟡
		lock.lockInterruptibly() ❗
		tryLock(timeout, unit) ⏱️
 */

/*
 	💡 Shared Resource?
	 The shared resource is the bank account data (like balance) — even if accessed via different tasks 
	 (e.g., withdraw, deposit, check balance), it’s still the same account being touched.
	 
	 ✅ Our Scenario: Users (threads) access:
		💵 Withdraw money
		💰 Deposit money
		📊 Check balance
		🔃 Transfer between accounts
	Each task touches shared balance — so we must guard access using ReentrantLock.
 */


/*
  🧵 Threads Do Different Tasks, But Share:
	One BankAccount instance
	One ReentrantLock object
	Same balance field
That balance is the shared resource we protect with different flavors of locking!
 */

public class ATMExample {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount();

        Thread t1 = new Thread(() -> account.withdraw("Alice", 300));
        Thread t2 = new Thread(() -> account.deposit("Bob", 500));
        Thread t3 = new Thread(() -> account.checkBalance("Charlie"));
        Thread t4 = new Thread(() -> account.transfer("Diana", 200));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        Thread.sleep(1000); // give threads time to block
        t4.interrupt(); // simulate transfer cancel (interrupt)
    }
}

/*
  ✅ What This Example Shows:
  | Method                | Thread           | Behavior                                   |
| --------------------- | ---------------- | ------------------------------------------ |
| `lock()`              | `withdraw()`     | Always waits to acquire the lock           |
| `tryLock()`           | `deposit()`      | Tries once, moves on if lock is held       |
| `tryLock(timeout)`    | `checkBalance()` | Waits a bit, then gives up if still locked |
| `lockInterruptibly()` | `transfer()`     | Waits, but can be interrupted externally   |
 */

/*
 🔚 Summary
	Shared resource: balance field in BankAccount
	Threads access it through different methods/tasks
	All access is controlled with ReentrantLock to ensure thread safety
 */

