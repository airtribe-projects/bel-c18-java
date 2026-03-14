package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class Car {
  @Value("${car.carModel}")
  private String model;

  @Value("${car.carType}")
  public String carType;

  @Autowired
  @Qualifier("dieselEngine")
  private Engine engine;

//  public Car(@Value("${car.carModel}") String model, @Value("${car.carType}") String carType, @Qualifier("dieselEngine") Engine engine) {
//    this.model = model;
//    this.carType = carType;
//    this.engine = engine;
//  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getCarType() {
    return carType;
  }

  public void setCarType(String carType) {
    this.carType = carType;
  }

  public void startCar() {
    System.out.println("Car " + model + " of type " + carType + " is starting. Engine Type: " + engine.getEngineType() + ", Engine Power: " + engine.getEnginePower());
  }

  public Engine getEngine() {
    return engine;
  }

  public void setEngine(Engine engine) {
    this.engine = engine;
  }
}
