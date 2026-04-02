package org.airtribe.LearnerManagementSystemBelC18.entity;

import java.util.List;

public class CohortResponseDTO {
  private Long cohortId;
  private String cohortName;
  private String cohortDescription;
  private Long courseId;
  private List<Long> learnerIds;

  public CohortResponseDTO() {}

  public CohortResponseDTO(Long cohortId, String cohortName, String cohortDescription, Long courseId, List<Long> learnerIds) {
    this.cohortId = cohortId;
    this.cohortName = cohortName;
    this.cohortDescription = cohortDescription;
    this.courseId = courseId;
    this.learnerIds = learnerIds;
  }

  public Long getCohortId() { return cohortId; }
  public void setCohortId(Long cohortId) { this.cohortId = cohortId; }
  public String getCohortName() { return cohortName; }
  public void setCohortName(String cohortName) { this.cohortName = cohortName; }
  public String getCohortDescription() { return cohortDescription; }
  public void setCohortDescription(String cohortDescription) { this.cohortDescription = cohortDescription; }
  public Long getCourseId() { return courseId; }
  public void setCourseId(Long courseId) { this.courseId = courseId; }
  public List<Long> getLearnerIds() { return learnerIds; }
  public void setLearnerIds(List<Long> learnerIds) { this.learnerIds = learnerIds; }
}
