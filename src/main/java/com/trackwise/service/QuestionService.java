package com.trackwise.service;

import java.util.List;

import com.trackwise.dto.QuestionRequestResponseDTO;
import com.trackwise.enums.QuestionNature;

public interface QuestionService {
	List<QuestionRequestResponseDTO> getAllQuestions();

	QuestionRequestResponseDTO getQuestionById(Long id);

	QuestionRequestResponseDTO createQuestion(QuestionRequestResponseDTO dto);

	QuestionRequestResponseDTO updateQuestion(Long id, QuestionRequestResponseDTO dto);

	void deleteQuestion(Long id);

	List<QuestionRequestResponseDTO> getQuestionsByNature(QuestionNature nature);
}
