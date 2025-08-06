package com.trackwise.dto;

import java.time.LocalDate;
import java.util.List;

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class AssessmentRequestResponseDTO {
	
	private Long id;
	private String client;
	private String program;
	private String track;
	private int milestone;
	private String assessmentType;
	private LocalDate date;
	private String assessmentPurpose;
	private List<AssessmentQuestionRequestResponseDTO> assessmentQuestions;
}
