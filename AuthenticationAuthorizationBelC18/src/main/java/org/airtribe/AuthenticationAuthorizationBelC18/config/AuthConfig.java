package org.airtribe.AuthenticationAuthorizationBelC18.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class AuthConfig {
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(11);
  }

  @Bean
  public SecurityFilterChain httpSecurityFilterChain(HttpSecurity httpSecurity) {
    try {
      httpSecurity.csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(authorizeRequests -> authorizeRequests
              .requestMatchers("/register", "/verifyRegistrationToken")
              .permitAll()
              .anyRequest()
              .authenticated())
          .formLogin(formLogin -> formLogin.defaultSuccessUrl("/", true).permitAll());
      return httpSecurity.build();
    } catch (Exception e) {
      throw new RuntimeException("Error configuring security filter chain", e);
    }
  }
}
