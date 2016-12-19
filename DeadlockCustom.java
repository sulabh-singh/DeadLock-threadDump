package trn.examples;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DeadlockCustom {

	public final static Logger LOGGER = Logger.getLogger(DeadlockCustom.class.getName());
	
	public static void main(String... args) {
		
		LOGGER.log(Level.ALL, "Main started...");
		
		String lock1 = "Lock1";
		String lock2 = "Lock2";
		Thread thread1 = new Thread(new Thread1(lock1, lock2), "Thread1");
		Thread thread2 = new Thread(new Thread2(lock1, lock2), "Thread2");
		
		thread1.start();
		thread2.start();
		
		LOGGER.log(Level.ALL, "Main ended...");
	}
}

class Thread1 implements Runnable {
	
	public final static Logger LOGGER = Logger.getLogger(Thread1.class.getName());
	
	Object lock1, lock2;
	
	Thread1(Object lock1, Object lock2) {
		this.lock1 = lock1;
		this.lock2 = lock2;
	}
	
	public void run() {

		LOGGER.log(Level.ALL, "Thread " + Thread.currentThread().getName());
		synchronized(lock1) {
			System.out.println("1. Thread " + Thread.currentThread().getName());
			synchronized(lock2) {
				System.out.println("2. Thread " + Thread.currentThread().getName());
			}
		}
	}
}

class Thread2 implements Runnable {
	
	public final static Logger LOGGER = Logger.getLogger(Thread2.class.getName());
	
	Object lock1, lock2;
	
	Thread2(Object lock1, Object lock2) {
		this.lock1 = lock1;
		this.lock2 = lock2;
	}
	
	public void run() {
		LOGGER.log(Level.ALL, "Thread " + Thread.currentThread().getName());
		synchronized(lock2) {
			System.out.println("1. Thread " + Thread.currentThread().getName());
			synchronized(lock1) {
				System.out.println("2. Thread " + Thread.currentThread().getName());
			}
		}
		
		
	}
}
