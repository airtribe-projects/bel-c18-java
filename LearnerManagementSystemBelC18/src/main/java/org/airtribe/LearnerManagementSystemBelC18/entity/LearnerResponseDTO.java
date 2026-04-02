package org.airtribe.LearnerManagementSystemBelC18.entity;

import java.util.List;

public class LearnerResponseDTO {
  private Long learnerId;
  private String learnerName;
  private String learnerEmail;
  private String learnerPhone;
  private List<Long> cohortIds;

  public LearnerResponseDTO() {}

  public LearnerResponseDTO(Long learnerId, String learnerName, String learnerEmail, String learnerPhone, List<Long> cohortIds) {
    this.learnerId = learnerId;
    this.learnerName = learnerName;
    this.learnerEmail = learnerEmail;
    this.learnerPhone = learnerPhone;
    this.cohortIds = cohortIds;
  }

  public Long getLearnerId() { return learnerId; }
  public void setLearnerId(Long learnerId) { this.learnerId = learnerId; }
  public String getLearnerName() { return learnerName; }
  public void setLearnerName(String learnerName) { this.learnerName = learnerName; }
  public String getLearnerEmail() { return learnerEmail; }
  public void setLearnerEmail(String learnerEmail) { this.learnerEmail = learnerEmail; }
  public String getLearnerPhone() { return learnerPhone; }
  public void setLearnerPhone(String learnerPhone) { this.learnerPhone = learnerPhone; }
  public List<Long> getCohortIds() { return cohortIds; }
  public void setCohortIds(List<Long> cohortIds) { this.cohortIds = cohortIds; }
}
