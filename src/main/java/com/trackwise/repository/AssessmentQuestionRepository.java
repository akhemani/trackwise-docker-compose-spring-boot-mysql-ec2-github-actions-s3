package com.trackwise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackwise.entity.AssessmentQuestion;

public interface AssessmentQuestionRepository extends JpaRepository<AssessmentQuestion, Long> {
}
