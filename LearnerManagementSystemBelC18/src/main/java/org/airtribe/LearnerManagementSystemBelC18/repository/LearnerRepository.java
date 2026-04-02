package org.airtribe.LearnerManagementSystemBelC18.repository;

import java.util.List;
import java.util.Optional;
import org.airtribe.LearnerManagementSystemBelC18.entity.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface LearnerRepository extends JpaRepository<Learner, Long> {
  public Learner findByLearnerName(String learnerName);

  @Query("SELECT l FROM Learner l WHERE l.learnerName = ?1")
  public List<Learner> findMyLearner(String learnerName);

  public Optional<Learner> findByLearnerEmail(String learnerEmail);
}
