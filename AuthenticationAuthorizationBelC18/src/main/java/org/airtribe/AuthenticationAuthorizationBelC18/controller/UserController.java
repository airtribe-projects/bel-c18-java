package org.airtribe.AuthenticationAuthorizationBelC18.controller;

import java.util.UUID;
import org.airtribe.AuthenticationAuthorizationBelC18.entity.User;
import org.airtribe.AuthenticationAuthorizationBelC18.entity.UserDTO;
import org.airtribe.AuthenticationAuthorizationBelC18.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping("/hello")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public String hello() {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    System.out.println("Authenticated user: " + authentication.getName());
//    if (authentication.getAuthorities().toString().equals("[ROLE_USER]")) {
//      return "Hello, World!";
//    }
    return "User does not have privilege to access this endpoint";


  }

  /**
   * JWT Token based authentication flow
   *
   * Input -> username & password
   * CHeck if username exists -> If not -> Redirect to register
   * If the user is enabled or not
   * If username exists -> check for password (hash of the input password with the stored hash)
   * If passwords match -> Generate JWT token and return to the client
   */
  @PostMapping("/signin")
  public String signin(@RequestParam("username") String username, @RequestParam("password") String password) {
    return _userService.signin(username, password);
  }

}
