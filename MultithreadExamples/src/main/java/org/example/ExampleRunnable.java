package org.example;

public class ExampleRunnable implements Runnable  {
  @Override
  public void run() {
    for (int i=0;i<10000;i++) {
      System.out.println("My Runnable Code " + i + Thread.currentThread().getName());
    }
  }
}
