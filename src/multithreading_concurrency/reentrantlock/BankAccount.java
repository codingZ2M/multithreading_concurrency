package multithreading_concurrency.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

class BankAccount {
    private double balance = 1000.0; // shared resource
    
 // Fair lock: threads acquire the lock in FIFO order. Provides advanced, manual control over locking behavior.
    private final ReentrantLock lock = new ReentrantLock(true); // fair lock

    // 1. Normal lock
    public void withdraw(String name, double amount) {
    	// If the lock is available, the thread takes it immediately and continues.
    	// If the lock is already held by another thread, the current thread is blocked (put to sleep) until the lock is released.
    	// Lock is now held by Thread-1. Thread-1 continues into the critical section.
        lock.lock();  // Attempts to acquire the lock.
        try {
            System.out.println(name + " trying to withdraw $" + amount);
            if (balance >= amount) {
                Thread.sleep(1000);
                balance -= amount;
                System.out.println(name + " withdrew $" + amount + ", new balance: $" + balance);
            } else {
                System.out.println(name + " insufficient funds for $" + amount);
            }
        } catch (InterruptedException e) {
            System.out.println(name + " was interrupted during withdrawal.");
        } finally {
            lock.unlock();
        }
    }

    // 2. tryLock() without waiting
    public void deposit(String name, double amount) {
    	//  It tries to acquire the lock, but doesn't wait if it's not available.
    	// Because sometimes you don’t want a thread to wait forever for a lock.
        if (lock.tryLock()) {
            try {
                System.out.println(name + " depositing $" + amount);
                Thread.sleep(500);
                balance += amount;
                System.out.println(name + " new balance after deposit: $" + balance);
            } catch (InterruptedException e) {
                System.out.println(name + " interrupted while depositing.");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println(name + " couldn't deposit. ATM busy.");
        }
    }

    // 3. tryLock(timeout)
    public void checkBalance(String name) {
        try {
        	// Tries to acquire the lock, but waits for a limited time
        	// If the lock becomes available within the timeout, it: Acquires the lock
        	// If the lock is not available within the timeout, it: Does not acquire the lock
            if (lock.tryLock(2, TimeUnit.SECONDS)) {
                try {
                    System.out.println(name + " checking balance...");
                    Thread.sleep(1000);
                    System.out.println(name + " sees balance: $" + balance);
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println(name + " couldn't check balance. Timed out.");
            }
        } catch (InterruptedException e) {
            System.out.println(name + " interrupted while checking balance.");
        }
    }

    // 4. Lock interruptibly
    public void transfer(String name, double amount) {
        try {
        	// 🔁 The thread can be interrupted while waiting for the lock.
        	// Wait for a lock unless it's told to stop
        	/* Real-Life Analogy: Imagine a person waiting in line to use an ATM:
        	    With lock(), they wait no matter what — even if their boss calls. 
        		With lockInterruptibly(), if the boss calls, they hang up the line and leave immediately.
        	*/
            lock.lockInterruptibly(); // can be interrupted while waiting
            try {
                System.out.println(name + " transferring $" + amount + "...");
                Thread.sleep(2000);
                balance -= amount;
                System.out.println(name + " finished transfer. New balance: $" + balance);
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            System.out.println(name + "'s transfer was cancelled (interrupted).");
        }
    }
}
