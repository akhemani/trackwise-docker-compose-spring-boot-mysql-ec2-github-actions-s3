package com.trackwise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackwise.entity.AssessmentPurpose;

@Repository
public interface AssessmentPurposeRepository extends JpaRepository<AssessmentPurpose, Long> {
	Optional<AssessmentPurpose> findByName(String name);
}
