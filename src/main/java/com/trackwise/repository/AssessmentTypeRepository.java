package com.trackwise.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackwise.entity.AssessmentType;

@Repository
public interface AssessmentTypeRepository extends JpaRepository<AssessmentType, Long> {
	Optional<AssessmentType> findByName(String name);
}
