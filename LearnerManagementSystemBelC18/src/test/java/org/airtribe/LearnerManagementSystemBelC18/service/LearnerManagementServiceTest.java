package org.airtribe.LearnerManagementSystemBelC18.service;

import java.util.List;
import java.util.Optional;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.airtribe.LearnerManagementSystemBelC18.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC18.exception.DuplicateEmailException;
import org.airtribe.LearnerManagementSystemBelC18.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystemBelC18.repository.LearnerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class LearnerManagementServiceTest {

  @Mock
  private LearnerRepository _learnerRepository;

  @InjectMocks
  private LearnerManagementService _learnerManagementService;

  private Learner learner;

  @BeforeAll
  public static void setup() {
    System.out.println("Setting up LearnerManagementServiceTest");
  }

  @AfterAll
  public static void tearDown() {
    System.out.println("All tests in LearnerManagementServiceTest are done");
  }

  @BeforeEach
  public void beforeEachTest() {
    learner = new Learner(1L, "test", "test", "1234");
    System.out.println("Starting a test method in LearnerManagementServiceTest");
  }

  @AfterEach
  public void afterEachTest() {
    System.out.println("Finished a test method in LearnerManagementServiceTest");
  }


  @Test
  public void testCreateLearnerSuccessfully() {
    // ARRANGE
    when(_learnerRepository.save(learner)).thenReturn(learner);

    // ACT
    Learner createdLearner = _learnerManagementService.createLearner(learner);
    // ASSERT
    Assertions.assertEquals("test", createdLearner.getLearnerName());
    Assertions.assertEquals("test", createdLearner.getLearnerEmail());
    Assertions.assertEquals("1234", createdLearner.getLearnerPhone());
    verify(_learnerRepository, times(1)).save(learner);
  }

  @Test
  public void testFetchLearnerByIdSuccessfully() throws LearnerNotFoundException {
    // ARRANGE
    when(_learnerRepository.findById(1L)).thenReturn(Optional.of(learner));
    // ACT
    Learner fetchedLearner = _learnerManagementService.fetchById(1L);
    // ASSERT
    Assertions.assertEquals("test", fetchedLearner.getLearnerName());
    Assertions.assertEquals("test", fetchedLearner.getLearnerEmail());
    Assertions.assertEquals("1234", fetchedLearner.getLearnerPhone());
    Assertions.assertEquals(1L, fetchedLearner.getLearnerId());
    verify(_learnerRepository, times(1)).findById(1L);

  }

  @Test
  public void testFetchLearnerByID_LearnerNotFoundException() {
    when(_learnerRepository.findById(1L)).thenReturn(Optional.empty());
    // ACT
    try {
      _learnerManagementService.fetchById(1L);
    } catch (LearnerNotFoundException e) {
      Assertions.assertEquals("Could not find learner with id: 1", e.getMessage());
    }
//    Assertions.assertThrows(LearnerNotFoundException.class, () -> _learnerManagementService.fetchById(1L));
  }

  @Test
  public void testFetchAllLearners(){
    when(_learnerRepository.findAll()).thenReturn(List.of(learner));

    List<Learner> learners = _learnerManagementService.fetchAllLearners();
    Assertions.assertEquals(1, learners.size());
    Assertions.assertEquals(learner, learners.get(0));
  }

  @Test
  public void testUpdateLearner() throws LearnerNotFoundException {

    when(_learnerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(learner));
    Learner testData = new Learner("updatedName","updatedEmail@gmail.com","1234");
    when(_learnerRepository.save(any(Learner.class))).thenReturn(testData);

    Learner updatedLearner = _learnerManagementService.updateLearner(1L, testData);

    Assertions.assertEquals(testData.getLearnerName(), updatedLearner.getLearnerName());
    Assertions.assertEquals(testData.getLearnerEmail(), updatedLearner.getLearnerEmail());
    Assertions.assertEquals(testData.getLearnerPhone(), updatedLearner.getLearnerPhone());
    Assertions.assertEquals(testData.getLearnerId(), updatedLearner.getLearnerId());
  }

  @Test
  public void testLearnerFetchByName() {
    when(_learnerRepository.findByLearnerName(anyString())).thenReturn(learner);
    Learner result = _learnerManagementService.fetchByName("test");
    Assertions.assertEquals(learner, result);
  }
}