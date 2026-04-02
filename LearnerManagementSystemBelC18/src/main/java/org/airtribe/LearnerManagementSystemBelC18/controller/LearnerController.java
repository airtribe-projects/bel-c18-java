package org.airtribe.LearnerManagementSystemBelC18.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.airtribe.LearnerManagementSystemBelC18.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC18.entity.LearnerResponseDTO;
import org.airtribe.LearnerManagementSystemBelC18.exception.DuplicateEmailException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learners")
public class LearnerController {

  @Autowired
  private LearnerManagementService _learnerManagementService;

  @PostMapping
  public ResponseEntity<LearnerResponseDTO> createLearner(@Valid @RequestBody Learner learner) throws DuplicateEmailException {
    Learner created = _learnerManagementService.createLearner(learner);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(_learnerManagementService.toLearnerResponseDTO(created));
  }

  @GetMapping
  public ResponseEntity<List<LearnerResponseDTO>> getLearners(
      @RequestParam(value = "name", required = false) String learnerName) {
    List<Learner> learners;
    if (learnerName == null) {
      learners = _learnerManagementService.fetchAllLearners();
    } else {
      learners = List.of(_learnerManagementService.fetchByName(learnerName));
    }
    return ResponseEntity.ok(_learnerManagementService.toLearnerResponseDTOList(learners));
  }

  @GetMapping("/{learnerId}")
  public ResponseEntity<LearnerResponseDTO> getLearnerById(@PathVariable Long learnerId)
      throws LearnerNotFoundException {
    LearnerResponseDTO learner = _learnerManagementService.toLearnerResponseDTO(
        _learnerManagementService.fetchById(learnerId));
    return ResponseEntity.ok(learner);
  }

  @PutMapping("/{learnerId}")
  public ResponseEntity<LearnerResponseDTO> updateLearner(
      @PathVariable Long learnerId, @RequestBody Learner learner)
      throws LearnerNotFoundException {
    LearnerResponseDTO updated = _learnerManagementService.toLearnerResponseDTO(
        _learnerManagementService.updateLearner(learnerId, learner));
    return ResponseEntity.ok(updated);
  }

  @DeleteMapping("/{learnerId}")
  public ResponseEntity<Void> deleteLearner(@PathVariable Long learnerId)
      throws LearnerNotFoundException {
    _learnerManagementService.deleteLearner(learnerId);
    return ResponseEntity.noContent().build();
  }
}
