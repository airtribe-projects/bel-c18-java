package org.example;

public class ExampleTimedWaitingThread {
  public static void main(String[] args) throws InterruptedException {
    Thread thread1 = new Thread(() -> {
      try {
        System.out.println("State of the thread1 before sleeping: " + Thread.currentThread().getState());
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        System.out.println("Thread was interrupted.");
      }
    });

    System.out.println("State of the main thread before starting thread1: " + Thread.currentThread().getState()); // RUNNABLE
    System.out.println("State of thread1 before starting: " + thread1.getState()); // NEW
    thread1.start();
    Thread.sleep(50);
    System.out.println("State of thread1 after starting: " + thread1.getState()); // TIMED_WAITING  // RUNNABLE
    System.out.println("State of the main thread after starting thread1: " + Thread.currentThread().getState()); // RUNNABLE



  }
}
