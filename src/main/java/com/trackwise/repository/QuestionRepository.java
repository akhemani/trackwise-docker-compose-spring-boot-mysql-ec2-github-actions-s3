package com.trackwise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackwise.entity.Question;
import com.trackwise.enums.QuestionNature;

public interface QuestionRepository extends JpaRepository<Question, Long> {

	Optional<Question> findByTitle(String title);

	List<Question> findByQuestionNature(QuestionNature questionNature);
}