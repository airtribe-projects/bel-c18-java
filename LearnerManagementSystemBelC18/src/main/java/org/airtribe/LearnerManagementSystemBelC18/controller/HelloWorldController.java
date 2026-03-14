package org.airtribe.LearnerManagementSystemBelC18.controller;

import org.airtribe.LearnerManagementSystemBelC18.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloWorldController {

  private HelloWorldService _helloWorldService;

  public HelloWorldController(HelloWorldService helloWorldService) {
    _helloWorldService = helloWorldService;
  }

  @GetMapping("/")
  public String helloWorld() {
    _helloWorldService.executeHelloWorld();
    return "Hello, World!";
  }

  @GetMapping("/hello")
  public String hello() {
    _helloWorldService.executeHelloWorld();
    return "Hello, World from the new API endpoint!";
  }
}