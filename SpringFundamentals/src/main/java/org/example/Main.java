package org.example;

import javax.annotation.processing.SupportedSourceVersion;
import org.example.config.JavaConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {
  public static void main(String[] args) {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JavaConfig.class);

    // Spring Container
    Car beanedCar1 = applicationContext.getBean(Car.class);
    Car beanedCar2 = applicationContext.getBean(Car.class);
    Engine petrolEngine = applicationContext.getBean(PetrolEngine.class);
    Engine dieselEngine = applicationContext.getBean(DieselEngine.class);
    System.out.println("Petrol Engine: " + petrolEngine.getEngineType() + ", Power: " + petrolEngine.getEnginePower());
    System.out.println("Diesel Engine: " + dieselEngine.getEngineType() + ", Power: " + dieselEngine.getEnginePower());
    beanedCar1.startCar();
  }
}

// Configure the beans
//  XML Based configuration
// Java based configuration
// Annotation Based configuration