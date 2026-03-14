package org.airtribe.LearnerManagementSystemBelC18.controller;

import org.airtribe.LearnerManagementSystemBelC18.Tes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

  @GetMapping("/test")
  public Tes test() {
    return new Tes("This is a test endpoint.", "Some other value");
  }

  @PostMapping("/test")
  public Tes testDuplicate() {
    return new Tes("This is a test endpoint.", "Some other value");
  }
}
