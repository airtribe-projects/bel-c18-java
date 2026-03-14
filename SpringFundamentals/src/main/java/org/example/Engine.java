package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component("engine")
public class Engine {

  private String engineType;

  private String enginePower;

  public Engine(@Value("${engine.engineType}") String engineType,  @Value("${engine.enginePower}") String enginePower) {
    this.engineType = engineType;
    this.enginePower = enginePower;
  }

  public String getEngineType() {
    return engineType;
  }

  public void setEngineType(String engineType) {
    this.engineType = engineType;
  }

  public String getEnginePower() {
    return enginePower;
  }

  public void setEnginePower(String enginePower) {
    this.enginePower = enginePower;
  }
}
