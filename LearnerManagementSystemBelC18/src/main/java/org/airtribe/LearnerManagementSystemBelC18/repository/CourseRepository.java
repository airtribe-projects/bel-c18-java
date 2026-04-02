package org.airtribe.LearnerManagementSystemBelC18.repository;

import org.airtribe.LearnerManagementSystemBelC18.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
