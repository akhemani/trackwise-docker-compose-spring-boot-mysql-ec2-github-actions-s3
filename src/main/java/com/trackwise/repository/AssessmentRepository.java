package com.trackwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackwise.entity.Assessment;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
}
