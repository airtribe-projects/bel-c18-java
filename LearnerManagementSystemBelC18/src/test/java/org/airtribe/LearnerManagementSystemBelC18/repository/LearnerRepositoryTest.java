package org.airtribe.LearnerManagementSystemBelC18.repository;

import java.util.List;
import org.airtribe.LearnerManagementSystemBelC18.entity.Learner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;


// Data cleanup -> Each tests should be indendepent in nature
// H2 database setup for actually writing to the data to the database
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LearnerRepositoryTest {

  @Autowired
  private LearnerRepository _learnerRepository;

  private Learner _learner;

  @BeforeEach
  public void setup() {
    _learner = new Learner();
    _learner.setLearnerEmail("test@gmail.com");
    _learner.setLearnerName("test");
    _learner.setLearnerPhone("1234");
  }

  @Test
  @Rollback(value = false)
  public void testCreateLearnerSuccessfully() {
    Learner savedLearner = _learnerRepository.save(_learner);
    Assertions.assertNotNull(savedLearner);
    Assertions.assertNotNull(savedLearner.getLearnerId());
    Assertions.assertEquals("test", savedLearner.getLearnerName());
    Assertions.assertEquals("test@gmail.com", savedLearner.getLearnerEmail());
    Assertions.assertEquals("1234", savedLearner.getLearnerPhone());
  }


  @Test
  public void testFetchAllLearners() {
    List<Learner> learnerList = _learnerRepository.findAll();
    Assertions.assertEquals(1, learnerList.size());

  }

}

