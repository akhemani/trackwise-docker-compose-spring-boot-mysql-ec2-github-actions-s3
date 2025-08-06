package com.trackwise.entity;

import java.time.LocalDate;
import java.util.List;

import com.trackwise.enums.AssessmentQuestionType;
import com.trackwise.enums.QuestionStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class AssessmentQuestion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Question question;

	@ManyToMany
	@JoinTable(name = "assessment_question_language", joinColumns = @JoinColumn(name = "assessment_question_id"), inverseJoinColumns = @JoinColumn(name = "language_id"))
	private List<Language> languages;

	@Enumerated(EnumType.STRING)
	private QuestionStatus status;

	private boolean latest;

	private String comment;

	private LocalDate date;

	private AssessmentQuestionType assessmentQuestionType;

}
