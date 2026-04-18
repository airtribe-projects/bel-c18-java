package org.example;

// main thread -> parent thread
// shorter thread
// longer thread
public class ExampleWaitingThread {
  public static void main(String[] args) throws InterruptedException {
    Thread longerThread = new Thread(() -> {
      try {
        Thread.sleep(5000);
        System.out.println("Longer thread has awakened.");
      } catch (InterruptedException e) {
        System.out.println("Longer thread was interrupted.");
      }
      System.out.println("State of the thread: " + Thread.currentThread().getState()); // RUNNABLE
    });

    System.out.println("State of longerThread before starting: " + longerThread.getState()); // NEW

    Thread shorterThread = new Thread(() -> {
      try {
        Thread.sleep(2000);
        longerThread.join();
        System.out.println("Shorter thread has awakened and will now notify the longer sleeping thread.");
      } catch (InterruptedException e) {
        System.out.println("Shorter thread was interrupted.");
      }

      System.out.println("State of the thread: " + Thread.currentThread().getState()); // RUNNABLE
    });

    System.out.println("State of shorterThread before starting: " + shorterThread.getState()); // NEW
    System.out.println("State of the longerThread before starting shorterThread: " + longerThread.getState()); // NEW
    System.out.println("State of the main thread before starting shorterThread: " + Thread.currentThread().getState()); // RUNNABLE


    Thread monitorThread = createMonitorThread(shorterThread, Thread.State.WAITING);

    longerThread.start();
    monitorThread.start();
    shorterThread.start();

    Thread.sleep(50);


    System.out.println("State of shorterThread after starting: " + shorterThread.getState()); // RUNNABLE TIMED_WATING WAITING
    System.out.println("State of the longerThread after starting shorterThread: " + longerThread.getState()); // RUNNABLE TIMED_WATING

  }

  private static Thread createMonitorThread(Thread shorterThread, Thread.State state) {
    return new Thread(() -> {
      while(shorterThread.isAlive()) {
        if (shorterThread.getState() == state) {
          System.out.println(">>> " + shorterThread.getName() + " is in " + state + " <<<");
        }
      }
    });
  }
}
