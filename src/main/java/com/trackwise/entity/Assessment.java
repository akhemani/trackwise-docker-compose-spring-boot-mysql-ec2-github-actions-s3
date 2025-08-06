package com.trackwise.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class Assessment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Client client;

	@ManyToOne
	private Program program;

	@ManyToOne
	private Track track;

	private int milestone;

	@ManyToOne
	private AssessmentType assessmentType;

	private LocalDate date;

	@ManyToOne
	private AssessmentPurpose assessmentPurpose;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "assessment_id") // adds assessment_id in AssessmentQuestion table
	private List<AssessmentQuestion> assessmentQuestions;

}
