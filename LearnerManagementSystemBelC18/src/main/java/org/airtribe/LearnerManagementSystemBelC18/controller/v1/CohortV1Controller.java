package org.airtribe.LearnerManagementSystemBelC18.controller.v1;

import org.airtribe.LearnerManagementSystemBelC18.entity.Cohort;
import org.airtribe.LearnerManagementSystemBelC18.service.LearnerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CohortV1Controller {

  @Autowired
  private LearnerManagementService _learnerManagementService;

  @GetMapping("/v1/cohorts")
  public Page<Cohort> getAllCohorts(
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
      @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
      @RequestParam(value = "sortBy", defaultValue = "cohortId") String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir
  ) {

    _learnerManagementService.validatePaginationAndSortingParams(pageSize, pageNumber, sortBy, sortDir);

    return _learnerManagementService.fetchPaginatedAndSortedCohorts(pageSize, pageNumber, sortBy, sortDir);


  }
}
