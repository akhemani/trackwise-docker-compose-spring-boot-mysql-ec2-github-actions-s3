package com.trackwise.service;

import java.util.List;

import com.trackwise.dto.AssessmentRequestResponseDTO;

public interface AssessmentService {
	
	List<AssessmentRequestResponseDTO> getAllAssessments();

	AssessmentRequestResponseDTO getAssessmentById(Long id);

	AssessmentRequestResponseDTO createAssessment(AssessmentRequestResponseDTO dto);

	AssessmentRequestResponseDTO updateAssessment(Long id, AssessmentRequestResponseDTO dto);

	void deleteAssessment(Long id);
}
