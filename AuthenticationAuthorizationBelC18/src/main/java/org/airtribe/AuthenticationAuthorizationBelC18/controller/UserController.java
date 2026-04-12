package org.airtribe.AuthenticationAuthorizationBelC18.controller;

import java.util.UUID;
import org.airtribe.AuthenticationAuthorizationBelC18.entity.User;
import org.airtribe.AuthenticationAuthorizationBelC18.entity.UserDTO;
import org.airtribe.AuthenticationAuthorizationBelC18.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
  @Autowired
  private UserService _userService;

  @PostMapping("/register")
  public User registerUser(@RequestBody UserDTO user) {
    User registeredUser =  _userService.registerUser(user);
    String token = UUID.randomUUID().toString();
    String verificationUrl = "http://localhost:3001/verifyRegistrationToken?token=" + token;
    System.out.println("Please verify your registration by clicking on the following link: " + verificationUrl);
    _userService.saveVerificationToken(registeredUser, token);
    return registeredUser;

  }

  @PostMapping("/verifyRegistrationToken")
  public String verifyRegistrationToken(@RequestParam("token") String token) {
    return _userService.verifyRegistrationTokenAndEnableUser(token);
  }
}
