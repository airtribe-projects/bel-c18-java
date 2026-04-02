package org.airtribe.LearnerManagementSystemBelC18.entity;

import java.util.List;

public class CourseDTO {
  private Long courseId;
  private String courseName;
  private String courseDescription;
  private List<Long> cohortIds;

  public CourseDTO() {}

  public CourseDTO(Long courseId, String courseName, String courseDescription, List<Long> cohortIds) {
    this.courseId = courseId;
    this.courseName = courseName;
    this.courseDescription = courseDescription;
    this.cohortIds = cohortIds;
  }

  public Long getCourseId() { return courseId; }
  public void setCourseId(Long courseId) { this.courseId = courseId; }
  public String getCourseName() { return courseName; }
  public void setCourseName(String courseName) { this.courseName = courseName; }
  public String getCourseDescription() { return courseDescription; }
  public void setCourseDescription(String courseDescription) { this.courseDescription = courseDescription; }
  public List<Long> getCohortIds() { return cohortIds; }
  public void setCohortIds(List<Long> cohortIds) { this.cohortIds = cohortIds; }
}
