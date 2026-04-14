package org.airtribe.AuthenticationAuthorizationBelC18.service;

import org.airtribe.AuthenticationAuthorizationBelC18.entity.User;
import org.airtribe.AuthenticationAuthorizationBelC18.entity.UserDTO;
import org.airtribe.AuthenticationAuthorizationBelC18.entity.VerificationToken;
import org.airtribe.AuthenticationAuthorizationBelC18.repository.UserRepository;
import org.airtribe.AuthenticationAuthorizationBelC18.repository.VerificationTokenRepository;
import org.airtribe.AuthenticationAuthorizationBelC18.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository _userRepository;

  @Autowired
  private VerificationTokenRepository _verificationTokenRepository;

  @Autowired
  private BCryptPasswordEncoder _passwordEncoder;

  public User registerUser(UserDTO user) {
    User toBeSavedUser = new User();
    toBeSavedUser.setUsername(user.getUsername());
    toBeSavedUser.setEmail(user.getEmail());
    toBeSavedUser.setPassword(_passwordEncoder.encode(user.getPassword()));
    toBeSavedUser.setRole("ADMIN");
    toBeSavedUser.setEnabled(false);
    return _userRepository.save(toBeSavedUser);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = _userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    } else {
      return org.springframework.security.core.userdetails.User.builder()
          .username(user.getUsername())
          .password(user.getPassword())
          .roles(user.getRole())
          .disabled(!user.isEnabled())
          .build();
    }
  }

  public void saveVerificationToken(User registeredUser, String token) {
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(token);
    verificationToken.setUser(registeredUser);
    verificationToken.setExpiryDate(new java.util.Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)); // Token valid for 24 hours
    _verificationTokenRepository.save(verificationToken);
  }

  public String verifyRegistrationTokenAndEnableUser(String token) {
    VerificationToken verificationToken = _verificationTokenRepository.findByToken(token);
    if (verificationToken == null) {
      return "Invalid token, please ensure you pass a valid token";
    } else {
      if (verificationToken.getExpiryDate().before(new java.util.Date())) {
        _verificationTokenRepository.delete(verificationToken);
        _userRepository.delete(verificationToken.getUser());
        return "Token has expired, please register again";
      }
      User registeredUser = verificationToken.getUser();
      registeredUser.setEnabled(true);
      _verificationTokenRepository.delete(verificationToken);
      _userRepository.save(registeredUser);
      return "User verified successfully, you can now login";
    }
  }

  public String signin(String username, String password) {
    User user = _userRepository.findByUsername(username);
    if (user == null) {
      return "User not found with username: " + username;
    }

    if (!user.isEnabled()) {
      return "User is not enabled, please verify your email before signing in.";
    }

    boolean isPasswordMatch = _passwordEncoder.matches(password, user.getPassword());
    if (!isPasswordMatch) {
      return "Invalid Password, Please provide correct credentials.";
    }

    return JwtUtil.generateJwtToken(user);
  }
}
