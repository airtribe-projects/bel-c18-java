package org.airtribe.LearnerManagementSystemBelC18.controller;

import java.util.List;
import org.airtribe.LearnerManagementSystemBelC18.entity.Cohort;
import org.airtribe.LearnerManagementSystemBelC18.entity.CohortResponseDTO;
import org.airtribe.LearnerManagementSystemBelC18.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC18.exception.CohortNotFoundException;
import org.airtribe.LearnerManagementSystemBelC18.exception.LearnerNotFoundException;
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
@RequestMapping("/cohorts")
public class CohortController {

  @Autowired
  private LearnerManagementService _learnerManagementService;

  @PostMapping
  public ResponseEntity<CohortResponseDTO> createCohort(@RequestBody Cohort cohort) {
    Cohort created = _learnerManagementService.createCohort(cohort);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(_learnerManagementService.toCohortResponseDTO(created));
  }

  @GetMapping
  public ResponseEntity<List<CohortResponseDTO>> getAllCohorts() {
    List<CohortResponseDTO> cohorts = _learnerManagementService.toCohortResponseDTOList(
        _learnerManagementService.fetchAllCohorts());
    return ResponseEntity.ok(cohorts);
  }

  @GetMapping("/{cohortId}")
  public ResponseEntity<CohortResponseDTO> getCohortById(@PathVariable Long cohortId)
      throws CohortNotFoundException {
    CohortResponseDTO cohort = _learnerManagementService.toCohortResponseDTO(
        _learnerManagementService.fetchCohortById(cohortId));
    return ResponseEntity.ok(cohort);
  }

  @PutMapping("/{cohortId}")
  public ResponseEntity<CohortResponseDTO> updateCohort(@PathVariable Long cohortId, @RequestBody Cohort cohort)
      throws CohortNotFoundException {
    CohortResponseDTO updated = _learnerManagementService.toCohortResponseDTO(
        _learnerManagementService.updateCohort(cohortId, cohort));
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{cohortId}")
  public ResponseEntity<Void> deleteCohort(@PathVariable Long cohortId) throws CohortNotFoundException {
    _learnerManagementService.deleteCohort(cohortId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{cohortId}/learners/{learnerId}")
  public ResponseEntity<CohortResponseDTO> assignLearnerToCohort(
      @PathVariable Long cohortId, @PathVariable Long learnerId)
      throws CohortNotFoundException, LearnerNotFoundException {
    CohortResponseDTO cohort = _learnerManagementService.toCohortResponseDTO(
        _learnerManagementService.assignLearnerToCohort(learnerId, cohortId));
    return ResponseEntity.ok(cohort);
  }

  @DeleteMapping("/{cohortId}/learners/{learnerId}")
  public ResponseEntity<Void> removeLearnerFromCohort(
      @PathVariable Long cohortId, @PathVariable Long learnerId)
      throws CohortNotFoundException, LearnerNotFoundException {
    _learnerManagementService.removeLearnerFromCohort(cohortId, learnerId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{cohortId}/learners")
  public ResponseEntity<CohortResponseDTO> bulkAssignLearners(
      @PathVariable Long cohortId, @RequestBody List<Learner> learners)
      throws CohortNotFoundException {
    CohortResponseDTO cohort = _learnerManagementService.toCohortResponseDTO(
        _learnerManagementService.assignAndCreateLearners(cohortId, learners));
    return ResponseEntity.status(HttpStatus.CREATED).body(cohort);
  }
}
