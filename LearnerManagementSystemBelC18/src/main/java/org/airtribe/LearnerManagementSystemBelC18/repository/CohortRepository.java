package org.airtribe.LearnerManagementSystemBelC18.repository;

import org.airtribe.LearnerManagementSystemBelC18.entity.Cohort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CohortRepository extends JpaRepository<Cohort, Long> {
}
