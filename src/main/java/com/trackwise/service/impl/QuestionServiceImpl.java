package com.trackwise.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackwise.dto.QuestionRequestResponseDTO;
import com.trackwise.entity.Language;
import com.trackwise.entity.Question;
import com.trackwise.enums.QuestionNature;
import com.trackwise.exception.ResourceNotFoundException;
import com.trackwise.repository.LanguageRepository;
import com.trackwise.repository.QuestionRepository;
import com.trackwise.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Override
	public List<QuestionRequestResponseDTO> getAllQuestions() {
		return questionRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public QuestionRequestResponseDTO getQuestionById(Long id) {
		Question question = questionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
		return convertToDTO(question);
	}

	@Override
	public QuestionRequestResponseDTO createQuestion(QuestionRequestResponseDTO dto) {
		Question question = convertToEntity(dto);
		return convertToDTO(questionRepository.save(question));
	}

	@Override
	public QuestionRequestResponseDTO updateQuestion(Long id, QuestionRequestResponseDTO dto) {
		Question question = questionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));

		question.setTitle(dto.getTitle());
		question.setInternal(dto.isInternal());
		question.setQuestionNature(QuestionNature.valueOf(dto.getQuestionNature()));
		question.setLanguages(languageRepository.findByNameIn(dto.getLanguages()));

		return convertToDTO(questionRepository.save(question));
	}

	@Override
	public void deleteQuestion(Long id) {
		Question question = questionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Question not found with id: " + id));
		questionRepository.delete(question);
	}

	private QuestionRequestResponseDTO convertToDTO(Question question) {
		return new QuestionRequestResponseDTO(question.getId(), question.isInternal(), question.getTitle(),
				question.getLanguages().stream().map(Language::getName).collect(Collectors.toList()),
				question.getQuestionNature().name());
	}

	private Question convertToEntity(QuestionRequestResponseDTO dto) {
		return new Question(null, dto.getTitle(), languageRepository.findByNameIn(dto.getLanguages()), dto.isInternal(),
				QuestionNature.valueOf(dto.getQuestionNature()));
	}

	@Override
	public List<QuestionRequestResponseDTO> getQuestionsByNature(QuestionNature nature) {
		List<Question> questions = questionRepository.findByQuestionNature(nature);

		return questions.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	private QuestionRequestResponseDTO mapToDto(Question question) {
		QuestionRequestResponseDTO dto = new QuestionRequestResponseDTO();
		dto.setId(question.getId());
		dto.setInternal(question.isInternal());
		dto.setTitle(question.getTitle());
		dto.setQuestionNature(question.getQuestionNature().name());
		dto.setLanguages(question.getLanguages().stream().map(Language::getName) // assuming Language has getName()
				.collect(Collectors.toList()));
		return dto;
	}
}
