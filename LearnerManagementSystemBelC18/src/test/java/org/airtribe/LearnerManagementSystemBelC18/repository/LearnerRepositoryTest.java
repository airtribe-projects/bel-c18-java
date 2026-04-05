package org.airtribe.LearnerManagementSystemBelC18.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.airtribe.LearnerManagementSystemBelC18.entity.Learner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LearnerRepositoryTest {

  @Autowired
  private LearnerRepository _learnerRepository;

  private Learner _learner;

  @BeforeEach
  public void setUp() {
    // deleteAllInBatch issues a single DELETE SQL — avoids loading entities into a
    // potentially stale Hibernate Session after a previous test threw an exception.
    _learnerRepository.deleteAllInBatch();
    _learner = buildLearner("Alice", "alice@example.com", "9876543210");
  }

  // ── Helpers ────────────────────────────────────────────────────────────────

  private static Learner buildLearner(String name, String email, String phone) {
    Learner learner = new Learner();
    learner.setLearnerName(name);
    learner.setLearnerEmail(email);
    learner.setLearnerPhone(phone);
    return learner;
  }

  // ── save — positive ────────────────────────────────────────────────────────

  @Test
  public void save_validLearner_persistsAndAssignsId() {
    // ARRANGE — done in setUp

    // ACT
    Learner saved = _learnerRepository.save(_learner);

    // ASSERT
    Assertions.assertNotNull(saved.getLearnerId());
    Assertions.assertEquals("Alice", saved.getLearnerName());
    Assertions.assertEquals("alice@example.com", saved.getLearnerEmail());
    Assertions.assertEquals("9876543210", saved.getLearnerPhone());
  }

  // ── save — negative (parameterized) ───────────────────────────────────────

  static Stream<Arguments> invalidLearnerProvider() {
    return Stream.of(
        Arguments.of("emptyName",    buildLearner("",    "valid@example.com", "9876543210")),
        Arguments.of("nullName",     buildLearner(null,  "valid@example.com", "9876543210")),
        Arguments.of("invalidEmail", buildLearner("Bob", "not-an-email",      "9876543210")),
        Arguments.of("emptyEmail",   buildLearner("Bob", "",                  "9876543210")),
        Arguments.of("emptyPhone",   buildLearner("Bob", "valid@example.com", ""))
    );
  }

  @ParameterizedTest(name = "[{index}] save_invalidLearner_{0}_throwsConstraintViolationException")
  @MethodSource("invalidLearnerProvider")
  public void save_invalidLearner_throwsConstraintViolationException(
      String scenario, Learner invalidLearner) {
    // ARRANGE — done via @MethodSource

    // ACT + ASSERT
    Exception exception = Assertions.assertThrows(Exception.class,
        () -> _learnerRepository.save(invalidLearner),
        "Expected a constraint violation for scenario: " + scenario);
    Assertions.assertTrue(
        exception.getMessage().contains("Validation failed for classes"),
        "Expected bean validation message for scenario: " + scenario);
  }

  // ── findAll ────────────────────────────────────────────────────────────────

  @Test
  public void findAll_noLearnersExist_returnsEmptyList() {
    // ARRANGE — setUp already calls deleteAll

    // ACT
    List<Learner> learners = _learnerRepository.findAll();

    // ASSERT
    Assertions.assertTrue(learners.isEmpty());
  }

  @Test
  public void findAll_multipleLearnersExist_returnsAllLearners() {
    // ARRANGE
    Learner second = buildLearner("Bob", "bob@example.com", "1111111111");
    _learnerRepository.save(_learner);
    _learnerRepository.save(second);

    // ACT
    List<Learner> learners = _learnerRepository.findAll();

    // ASSERT
    Assertions.assertEquals(2, learners.size());
  }

  // ── findById ───────────────────────────────────────────────────────────────

  @Test
  public void findById_existingLearner_returnsLearner() {
    // ARRANGE
    Learner saved = _learnerRepository.save(_learner);

    // ACT
    Optional<Learner> result = _learnerRepository.findById(saved.getLearnerId());

    // ASSERT
    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals("Alice", result.get().getLearnerName());
    Assertions.assertEquals("alice@example.com", result.get().getLearnerEmail());
  }

  @Test
  public void findById_nonExistingId_returnsEmptyOptional() {
    // ARRANGE — no learner saved

    // ACT
    Optional<Learner> result = _learnerRepository.findById(Long.MAX_VALUE);

    // ASSERT
    Assertions.assertFalse(result.isPresent());
  }

  // ── deleteById ─────────────────────────────────────────────────────────────

  @Test
  public void deleteById_existingLearner_removesFromRepository() {
    // ARRANGE
    Learner saved = _learnerRepository.save(_learner);
    Long learnerId = saved.getLearnerId();

    // ACT
    _learnerRepository.deleteById(learnerId);

    // ASSERT
    Assertions.assertFalse(_learnerRepository.findById(learnerId).isPresent());
  }

  // ── findByLearnerName ──────────────────────────────────────────────────────

  @Test
  public void findByLearnerName_existingName_returnsLearner() {
    // ARRANGE
    _learnerRepository.save(_learner);

    // ACT
    Learner found = _learnerRepository.findByLearnerName("Alice");

    // ASSERT
    Assertions.assertNotNull(found);
    Assertions.assertEquals("alice@example.com", found.getLearnerEmail());
    Assertions.assertEquals("9876543210", found.getLearnerPhone());
  }

  @Test
  public void findByLearnerName_nonExistingName_returnsNull() {
    // ARRANGE — no learner saved with this name

    // ACT
    Learner found = _learnerRepository.findByLearnerName("NonExistent");

    // ASSERT
    Assertions.assertNull(found);
  }

  // ── findMyLearner (JPQL @Query) ────────────────────────────────────────────

  @Test
  public void findMyLearner_existingName_returnsMatchingLearners() {
    // ARRANGE
    _learnerRepository.save(_learner);

    // ACT
    List<Learner> results = _learnerRepository.findMyLearner("Alice");

    // ASSERT
    Assertions.assertEquals(1, results.size());
    Assertions.assertEquals("alice@example.com", results.get(0).getLearnerEmail());
  }

  @Test
  public void findMyLearner_nonExistingName_returnsEmptyList() {
    // ARRANGE — no learner saved with this name

    // ACT
    List<Learner> results = _learnerRepository.findMyLearner("NonExistent");

    // ASSERT
    Assertions.assertTrue(results.isEmpty());
  }

  // ── findByLearnerEmail ─────────────────────────────────────────────────────

  @Test
  public void findByLearnerEmail_existingEmail_returnsNonEmptyOptional() {
    // ARRANGE
    _learnerRepository.save(_learner);

    // ACT
    Optional<Learner> result = _learnerRepository.findByLearnerEmail("alice@example.com");

    // ASSERT
    Assertions.assertTrue(result.isPresent());
    Assertions.assertEquals("Alice", result.get().getLearnerName());
    Assertions.assertEquals("9876543210", result.get().getLearnerPhone());
  }

  @Test
  public void findByLearnerEmail_nonExistingEmail_returnsEmptyOptional() {
    // ARRANGE — no learner saved with this email

    // ACT
    Optional<Learner> result = _learnerRepository.findByLearnerEmail("notfound@example.com");

    // ASSERT
    Assertions.assertFalse(result.isPresent());
  }

  @Test
  public void save_duplicateEmail_throwsDataIntegrityViolationException() {
    // ARRANGE
    _learnerRepository.save(_learner);
    Learner duplicate = buildLearner("Alice2", "alice@example.com", "0987654321");

    // ACT + ASSERT
    Assertions.assertThrows(Exception.class, () -> {
      _learnerRepository.saveAndFlush(duplicate);
    }, "Expected a unique constraint violation for duplicate email");
  }
}
