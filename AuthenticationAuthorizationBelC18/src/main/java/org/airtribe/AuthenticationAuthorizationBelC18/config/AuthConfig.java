package org.airtribe.AuthenticationAuthorizationBelC18.config;

import org.airtribe.AuthenticationAuthorizationBelC18.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity
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
              .requestMatchers("/register", "/verifyRegistrationToken", "/signin", "/hello")
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
