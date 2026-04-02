package org.airtribe.LearnerManagementSystemBelC18.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.airtribe.LearnerManagementSystemBelC18.entity.Cohort;
import org.airtribe.LearnerManagementSystemBelC18.entity.CohortDTO;
import org.airtribe.LearnerManagementSystemBelC18.entity.CohortResponseDTO;
import org.airtribe.LearnerManagementSystemBelC18.entity.Course;
import org.airtribe.LearnerManagementSystemBelC18.entity.CourseDTO;
import org.airtribe.LearnerManagementSystemBelC18.entity.Learner;
import org.airtribe.LearnerManagementSystemBelC18.entity.LearnerDTO;
import org.airtribe.LearnerManagementSystemBelC18.entity.LearnerResponseDTO;
import org.airtribe.LearnerManagementSystemBelC18.exception.CohortNotFoundException;
import org.airtribe.LearnerManagementSystemBelC18.exception.CourseNotFoundException;
import org.airtribe.LearnerManagementSystemBelC18.exception.DuplicateEmailException;
import org.airtribe.LearnerManagementSystemBelC18.exception.LearnerNotFoundException;
import org.airtribe.LearnerManagementSystemBelC18.repository.CohortRepository;
import org.airtribe.LearnerManagementSystemBelC18.repository.CourseRepository;
import org.airtribe.LearnerManagementSystemBelC18.repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LearnerManagementService {

  @Autowired
  private LearnerRepository _learnerRepository;

  @Autowired
  private CohortRepository _cohortRepository;

  @Autowired
  private CourseRepository _courseRepository;

  // ── Learner Operations ──────────────────────────────────────────────────────

  @CacheEvict(value = "learners", allEntries = true)
  public Learner createLearner(Learner learner) throws DuplicateEmailException {
//    if (_learnerRepository.findByLearnerEmail(learner.getLearnerEmail()).isPresent()) {
//      throw new DuplicateEmailException("A learner with email '" + learner.getLearnerEmail() + "' already exists");
//    }
    return _learnerRepository.save(learner);
  }

  @Cacheable(value = "learners")
  public List<Learner> fetchAllLearners() {
    return _learnerRepository.findAll();
  }

  @Cacheable(value = "learner", key = "#learnerId")
  public Learner fetchById(Long learnerId) throws LearnerNotFoundException {
    return _learnerRepository.findById(learnerId)
        .orElseThrow(() -> new LearnerNotFoundException("Could not find learner with id: " + learnerId));
  }

  public Learner fetchByName(String learnerName) {
    return _learnerRepository.findByLearnerName(learnerName);
  }

  public Learner updateLearner(Long learnerId, Learner updated) throws LearnerNotFoundException {
    Learner existing = fetchById(learnerId);
    existing.setLearnerName(updated.getLearnerName());
    existing.setLearnerEmail(updated.getLearnerEmail());
    existing.setLearnerPhone(updated.getLearnerPhone());
    return _learnerRepository.save(existing);
  }

  public void deleteLearner(Long learnerId) throws LearnerNotFoundException {
    fetchById(learnerId);
    _learnerRepository.deleteById(learnerId);
  }

  // ── Cohort Operations ───────────────────────────────────────────────────────

  public Cohort createCohort(Cohort cohort) {
    return _cohortRepository.save(cohort);
  }

  public List<Cohort> fetchAllCohorts() {
    return _cohortRepository.findAll();
  }

  public Cohort fetchCohortById(Long cohortId) throws CohortNotFoundException {
    return _cohortRepository.findById(cohortId)
        .orElseThrow(() -> new CohortNotFoundException("Could not find cohort with id: " + cohortId));
  }

  public Cohort updateCohort(Long cohortId, Cohort updated) throws CohortNotFoundException {
    Cohort existing = fetchCohortById(cohortId);
    existing.setCohortName(updated.getCohortName());
    existing.setCohortDescription(updated.getCohortDescription());
    return _cohortRepository.save(existing);
  }

  public void deleteCohort(Long cohortId) throws CohortNotFoundException {
    fetchCohortById(cohortId);
    _cohortRepository.deleteById(cohortId);
  }

  public Cohort assignLearnerToCohort(Long learnerId, Long cohortId)
      throws LearnerNotFoundException, CohortNotFoundException {
    Learner learner = fetchById(learnerId);
    Cohort cohort = fetchCohortById(cohortId);
    cohort.getLearners().add(learner);
    return _cohortRepository.save(cohort);
  }

  public Cohort removeLearnerFromCohort(Long cohortId, Long learnerId)
      throws CohortNotFoundException, LearnerNotFoundException {
    Cohort cohort = fetchCohortById(cohortId);
    fetchById(learnerId);
    cohort.getLearners().removeIf(l -> l.getLearnerId().equals(learnerId));
    return _cohortRepository.save(cohort);
  }

  @Transactional
  public Cohort assignAndCreateLearners(long cohortId, List<Learner> learners) throws CohortNotFoundException {
    Cohort fetchedCohort = fetchCohortById(cohortId);
    fetchedCohort.getLearners().addAll(learners);
    return _cohortRepository.save(fetchedCohort);
  }

  // ── Course Operations ───────────────────────────────────────────────────────

  public Course createCourse(Course course) {
    return _courseRepository.save(course);
  }

  public List<Course> fetchAllCourses() {
    return _courseRepository.findAll();
  }

  public Course fetchCourseById(Long courseId) throws CourseNotFoundException {
    return _courseRepository.findById(courseId)
        .orElseThrow(() -> new CourseNotFoundException("Could not find course with id: " + courseId));
  }

  public Course updateCourse(Long courseId, Course updated) throws CourseNotFoundException {
    Course existing = fetchCourseById(courseId);
    existing.setCourseName(updated.getCourseName());
    existing.setCourseDescription(updated.getCourseDescription());
    return _courseRepository.save(existing);
  }

  public void deleteCourse(Long courseId) throws CourseNotFoundException {
    fetchCourseById(courseId);
    _courseRepository.deleteById(courseId);
  }

  public List<Cohort> fetchCohortsByCourse(Long courseId) throws CourseNotFoundException {
    Course course = fetchCourseById(courseId);
    return course.getCohorts();
  }

  // ── DTO Conversions ─────────────────────────────────────────────────────────

  public LearnerResponseDTO toLearnerResponseDTO(Learner learner) {
    if (learner.getCohorts() == null) {
      learner.setCohorts(new ArrayList<>());
    }
    List<Long> cohortIds = learner.getCohorts().stream()
        .map(Cohort::getCohortId)
        .collect(Collectors.toList());
    return new LearnerResponseDTO(
        learner.getLearnerId(),
        learner.getLearnerName(),
        learner.getLearnerEmail(),
        learner.getLearnerPhone(),
        cohortIds
    );
  }

  public List<LearnerResponseDTO> toLearnerResponseDTOList(List<Learner> learners) {
    return learners.stream().map(this::toLearnerResponseDTO).collect(Collectors.toList());
  }

  public CohortResponseDTO toCohortResponseDTO(Cohort cohort) {
    Long courseId = cohort.getCourse() != null ? cohort.getCourse().getCourseId() : null;
    if (cohort.getLearners() == null) {
      cohort.setLearners(new ArrayList<>());
    }
    List<Long> learnerIds = cohort.getLearners().stream()
        .map(Learner::getLearnerId)
        .collect(Collectors.toList());
    return new CohortResponseDTO(
        cohort.getCohortId(),
        cohort.getCohortName(),
        cohort.getCohortDescription(),
        courseId,
        learnerIds
    );
  }

  public List<CohortResponseDTO> toCohortResponseDTOList(List<Cohort> cohorts) {
    return cohorts.stream().map(this::toCohortResponseDTO).collect(Collectors.toList());
  }

  public CourseDTO toCourseDTO(Course course) {
    List<Long> cohortIds = course.getCohorts().stream()
        .map(Cohort::getCohortId)
        .collect(Collectors.toList());
    return new CourseDTO(
        course.getCourseId(),
        course.getCourseName(),
        course.getCourseDescription(),
        cohortIds
    );
  }

  public List<CourseDTO> toCourseDTOList(List<Course> courses) {
    return courses.stream().map(this::toCourseDTO).collect(Collectors.toList());
  }

  // ── Legacy DTO conversion (kept for backward compatibility) ─────────────────

  public List<LearnerDTO> convertToLearnerDTO(List<Learner> learners) {
    List<LearnerDTO> learnerDTOS = new ArrayList<>();
    for (Learner learner : learners) {
      LearnerDTO learnerDTO = new LearnerDTO();
      learnerDTO.setLearnerId(learner.getLearnerId());
      learnerDTO.setLearnerEmail(learner.getLearnerEmail());
      learnerDTO.setLearnerName(learner.getLearnerName());
      learnerDTO.setLearnerPhone(learner.getLearnerPhone());
      List<CohortDTO> cohortDTOS = new ArrayList<>();
      for (Cohort cohort : learner.getCohorts()) {
        CohortDTO cohortDTO = new CohortDTO();
        cohortDTO.setCohortId(cohort.getCohortId());
        cohortDTO.setCohortName(cohort.getCohortName());
        cohortDTOS.add(cohortDTO);
      }
      learnerDTO.setCohorts(cohortDTOS);
      learnerDTOS.add(learnerDTO);
    }
    return learnerDTOS;
  }

  public Page<Cohort> fetchPaginatedAndSortedCohorts(int pageSize, int pageNumber, String sortBy, String sortDir) {
    Sort.Direction direction;
    if (sortDir.equalsIgnoreCase("asc")) {
      direction = Sort.Direction.ASC;
    } else {
      direction = Sort.Direction.DESC;
    }

    Sort sort = Sort.by(direction, sortBy);
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
    return _cohortRepository.findAll(pageable);
  }

  public void validatePaginationAndSortingParams(int pageSize, int pageNumber, String sortBy, String sortDir) {
    if (pageSize < 0) {
      pageSize = 10;
    }

    if (pageSize > 2000) {
      pageSize = 10;
    }

    if (pageNumber < 0) {
      pageNumber = 0;
    }

    if (!sortDir.equals("asc") && !sortDir.equals("desc")) {
      sortDir = "asc";
    }

    if (!sortBy.equals("cohortId") && !sortBy.equals("cohortName")) {
      sortBy = "cohortId";
    }
  }
}

// select * from cohorts;
// select * from cohorts limit 10 offset 0;
// select * from cohorts limit 10 offset 11;
// select * from cohort limit 10 offset 22;
