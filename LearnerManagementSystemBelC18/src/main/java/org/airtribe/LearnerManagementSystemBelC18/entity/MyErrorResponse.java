package org.airtribe.LearnerManagementSystemBelC18.entity;

public class MyErrorResponse {
  private String message;

  private int statusCode;

  public MyErrorResponse(String message, int statusCode) {
    this.message = message;
    this.statusCode = statusCode;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }
}
