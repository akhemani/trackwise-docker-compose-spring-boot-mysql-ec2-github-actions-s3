package com.trackwise.dto;

import java.time.LocalDate;
import java.util.List;

import com.trackwise.enums.AssessmentQuestionType;
import com.trackwise.enums.QuestionStatus;

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class AssessmentQuestionRequestResponseDTO {

	private Long id;
	private String question;
	private List<String> languages;
	private QuestionStatus status;
	private boolean latest;
	private String comment;
	private LocalDate date;
	private AssessmentQuestionType assessmentQuestionType;
}