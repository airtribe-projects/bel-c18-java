package org.airtribe.AuthenticationAuthorizationBelC18.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.util.Date;


@Entity
public class VerificationToken {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String token;

  @OneToOne
  private User user;

  private Date expiryDate;

  public VerificationToken(long id, String token, User user, Date expiryDate) {
    this.id = id;
    this.token = token;
    this.user = user;
    this.expiryDate = expiryDate;
  }

  public VerificationToken() {

  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Date getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(Date expiryDate) {
    this.expiryDate = expiryDate;
  }
}
