package org.airtribe.LearnerManagementSystemBelC18.controller;

import java.util.List;
import org.airtribe.LearnerManagementSystemBelC18.entity.CohortResponseDTO;
import org.airtribe.LearnerManagementSystemBelC18.entity.Course;
import org.airtribe.LearnerManagementSystemBelC18.entity.CourseDTO;
import org.airtribe.LearnerManagementSystemBelC18.exception.CourseNotFoundException;
import org.airtribe.LearnerManagementSystemBelC18.service.LearnerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

  @Autowired
  private LearnerManagementService _learnerManagementService;

  @PostMapping
  public ResponseEntity<CourseDTO> createCourse(@RequestBody Course course) {
    Course created = _learnerManagementService.createCourse(course);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(_learnerManagementService.toCourseDTO(created));
  }

  @GetMapping
  public ResponseEntity<List<CourseDTO>> getAllCourses() {
    List<CourseDTO> courses = _learnerManagementService.toCourseDTOList(
        _learnerManagementService.fetchAllCourses());
    return ResponseEntity.ok(courses);
  }

  @GetMapping("/{courseId}")
  public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long courseId) throws CourseNotFoundException {
    CourseDTO course = _learnerManagementService.toCourseDTO(
        _learnerManagementService.fetchCourseById(courseId));
    return ResponseEntity.ok(course);
  }

  @PutMapping("/{courseId}")
  public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long courseId, @RequestBody Course course)
      throws CourseNotFoundException {
    CourseDTO updated = _learnerManagementService.toCourseDTO(
        _learnerManagementService.updateCourse(courseId, course));
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{courseId}")
  public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) throws CourseNotFoundException {
    _learnerManagementService.deleteCourse(courseId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{courseId}/cohorts")
  public ResponseEntity<List<CohortResponseDTO>> getCohortsByCourse(@PathVariable Long courseId)
      throws CourseNotFoundException {
    List<CohortResponseDTO> cohorts = _learnerManagementService.toCohortResponseDTOList(
        _learnerManagementService.fetchCohortsByCourse(courseId));
    return ResponseEntity.ok(cohorts);
  }
}
