package org.example;

public class Main {
  public static void main(String[] args) {
//    ExampleThread thread = new ExampleThread();
//    thread.start();

    ExampleRunnable runnable = new ExampleRunnable();
    Thread thread1 = new Thread(runnable);
    thread1.run();

    for (int i=0;i<1000;i++) {
      System.out.println("My Main Code " + i + " " + Thread.currentThread().getName());
    }

  }
}