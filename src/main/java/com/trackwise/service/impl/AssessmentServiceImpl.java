package com.trackwise.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trackwise.dto.AssessmentQuestionRequestResponseDTO;
import com.trackwise.dto.AssessmentRequestResponseDTO;
import com.trackwise.entity.Assessment;
import com.trackwise.entity.AssessmentQuestion;
import com.trackwise.entity.Language;
import com.trackwise.enums.QuestionStatus;
import com.trackwise.exception.ResourceNotFoundException;
import com.trackwise.repository.AssessmentPurposeRepository;
import com.trackwise.repository.AssessmentRepository;
import com.trackwise.repository.AssessmentTypeRepository;
import com.trackwise.repository.ClientRepository;
import com.trackwise.repository.LanguageRepository;
import com.trackwise.repository.ProgramRepository;
import com.trackwise.repository.QuestionRepository;
import com.trackwise.repository.TrackRepository;
import com.trackwise.service.AssessmentService;

@Service
public class AssessmentServiceImpl implements AssessmentService {

	@Autowired
	private AssessmentRepository assessmentRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ProgramRepository programRepository;

	@Autowired
	private TrackRepository trackRepository;

	@Autowired
	private AssessmentTypeRepository assessmentTypeRepository;

	@Autowired
	private AssessmentPurposeRepository assessmentPurposeRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Override
	public List<AssessmentRequestResponseDTO> getAllAssessments() {
		return assessmentRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public AssessmentRequestResponseDTO getAssessmentById(Long id) {
		Assessment assessment = assessmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Assessment not found with ID: " + id));
		return convertToDTO(assessment);
	}

	@Override
	public AssessmentRequestResponseDTO createAssessment(AssessmentRequestResponseDTO dto) {
		Assessment assessment = convertToEntity(dto);
		return convertToDTO(assessmentRepository.save(assessment));
	}

//	@Override
//	public AssessmentRequestResponseDTO updateAssessment(Long id, AssessmentRequestResponseDTO dto) {
//		Assessment assessment = assessmentRepository.findById(id)
//				.orElseThrow(() -> new ResourceNotFoundException("Assessment not found with ID: " + id));
//		Assessment updated = convertToEntity(dto);
//		updated.setId(assessment.getId());
//		return convertToDTO(assessmentRepository.save(updated));
//	}
	@Override
	public AssessmentRequestResponseDTO updateAssessment(Long id, AssessmentRequestResponseDTO dto) {
		Assessment existing = assessmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Assessment not found with ID: " + id));

		Assessment updated = convertToEntity(dto, existing);
		return convertToDTO(assessmentRepository.save(updated));
	}

	@Override
	public void deleteAssessment(Long id) {
		Assessment assessment = assessmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Assessment not found with ID: " + id));
		assessmentRepository.delete(assessment);
	}

	private Assessment convertToEntity(AssessmentRequestResponseDTO dto) {

		Assessment assessment = new Assessment();

		assessment.setClient(clientRepository.findByName(dto.getClient())
				.orElseThrow(() -> new ResourceNotFoundException("Client not found: " + dto.getClient())));

		assessment.setProgram(programRepository.findByName(dto.getProgram())
				.orElseThrow(() -> new ResourceNotFoundException("Program not found: " + dto.getProgram())));

		assessment.setTrack(trackRepository.findByName(dto.getTrack())
				.orElseThrow(() -> new ResourceNotFoundException("Track not found: " + dto.getTrack())));

		assessment.setAssessmentType(assessmentTypeRepository.findByName(dto.getAssessmentType()).orElseThrow(
				() -> new ResourceNotFoundException("AssessmentType not found: " + dto.getAssessmentType())));

		assessment.setAssessmentPurpose(assessmentPurposeRepository.findByName(dto.getAssessmentPurpose()).orElseThrow(
				() -> new ResourceNotFoundException("AssessmentPurpose not found: " + dto.getAssessmentPurpose())));

		assessment.setMilestone(dto.getMilestone());
		assessment.setDate(dto.getDate());

		List<AssessmentQuestion> questions = dto.getAssessmentQuestions().stream().map(qdto -> {

			AssessmentQuestion q = new AssessmentQuestion();

			q.setQuestion(questionRepository.findByTitle(qdto.getQuestion())
					.orElseThrow(() -> new ResourceNotFoundException("Question not found: " + qdto.getQuestion())));
			q.setLanguages(languageRepository.findByNameIn(qdto.getLanguages()));
			q.setStatus(qdto.getStatus() == null ? QuestionStatus.SHARED : qdto.getStatus());
			q.setLatest(!(qdto.isLatest()) ? true : qdto.isLatest());
			q.setComment(qdto.getComment() == null ? "" : qdto.getComment());
			q.setDate(qdto.getDate());
			q.setAssessmentQuestionType(qdto.getAssessmentQuestionType());

			return q;
		}).collect(Collectors.toList());

		assessment.setAssessmentQuestions(questions);
		return assessment;
	}

	private Assessment convertToEntity(AssessmentRequestResponseDTO dto, Assessment existing) {
		// Update top-level Assessment fields
		existing.setClient(clientRepository.findByName(dto.getClient())
				.orElseThrow(() -> new ResourceNotFoundException("Client not found: " + dto.getClient())));
		existing.setProgram(programRepository.findByName(dto.getProgram())
				.orElseThrow(() -> new ResourceNotFoundException("Program not found: " + dto.getProgram())));
		existing.setTrack(trackRepository.findByName(dto.getTrack())
				.orElseThrow(() -> new ResourceNotFoundException("Track not found: " + dto.getTrack())));
		existing.setAssessmentType(assessmentTypeRepository.findByName(dto.getAssessmentType()).orElseThrow(
				() -> new ResourceNotFoundException("AssessmentType not found: " + dto.getAssessmentType())));
		existing.setAssessmentPurpose(assessmentPurposeRepository.findByName(dto.getAssessmentPurpose()).orElseThrow(
				() -> new ResourceNotFoundException("AssessmentPurpose not found: " + dto.getAssessmentPurpose())));
		existing.setMilestone(dto.getMilestone());
		existing.setDate(dto.getDate());

		// Work with existing assessment questions
		List<AssessmentQuestion> existingList = existing.getAssessmentQuestions();

		// Map existing questions by ID
		Map<Long, AssessmentQuestion> existingMap = existingList.stream().filter(q -> q.getId() != null)
				.collect(Collectors.toMap(AssessmentQuestion::getId, q -> q));

		// Loop through DTOs and update or add as needed
		for (AssessmentQuestionRequestResponseDTO qdto : dto.getAssessmentQuestions()) {
			if (qdto.getId() != null && existingMap.containsKey(qdto.getId())) {
				// Update existing question
				AssessmentQuestion question = existingMap.get(qdto.getId());

				question.setQuestion(questionRepository.findByTitle(qdto.getQuestion())
						.orElseThrow(() -> new ResourceNotFoundException("Question not found: " + qdto.getQuestion())));
				question.setLanguages(languageRepository.findByNameIn(qdto.getLanguages()));
				question.setStatus(qdto.getStatus());
				question.setLatest(qdto.isLatest());
				question.setComment(qdto.getComment());
				question.setDate(qdto.getDate());
				question.setAssessmentQuestionType(qdto.getAssessmentQuestionType());
			} else {
				// Add new question without removing existing ones
				AssessmentQuestion newQuestion = new AssessmentQuestion();

				newQuestion.setQuestion(questionRepository.findByTitle(qdto.getQuestion())
						.orElseThrow(() -> new ResourceNotFoundException("Question not found: " + qdto.getQuestion())));
				newQuestion.setLanguages(languageRepository.findByNameIn(qdto.getLanguages()));
				newQuestion.setStatus(qdto.getStatus());
				newQuestion.setLatest(qdto.isLatest());
				newQuestion.setComment(qdto.getComment());
				newQuestion.setDate(qdto.getDate());
				newQuestion.setAssessmentQuestionType(qdto.getAssessmentQuestionType());

				existingList.add(newQuestion); // Append new question
			}
		}

		// Do NOT clear or remove anything — preserve all others
		return existing;
	}

	private AssessmentRequestResponseDTO convertToDTO(Assessment assessment) {
		return new AssessmentRequestResponseDTO(assessment.getId(), assessment.getClient().getName(),
				assessment.getProgram().getName(), assessment.getTrack().getName(), assessment.getMilestone(),
				assessment.getAssessmentType().getName(), assessment.getDate(),
				assessment.getAssessmentPurpose().getName(),
				assessment.getAssessmentQuestions().stream()
						.map(q -> new AssessmentQuestionRequestResponseDTO(q.getId(), q.getQuestion().getTitle(),
								q.getLanguages().stream().map(Language::getName).collect(Collectors.toList()),
								q.getStatus(), q.isLatest(), q.getComment(), q.getDate(),
								q.getAssessmentQuestionType()))
						.collect(Collectors.toList()));
	}
}
