package org.example;

public class ExampleThread extends Thread {
  @Override
  public void run() {
    for (int i=0;i<10000;i++) {
      System.out.println("My Thread Code " + i + Thread.currentThread().getName());
    }
  }
}
