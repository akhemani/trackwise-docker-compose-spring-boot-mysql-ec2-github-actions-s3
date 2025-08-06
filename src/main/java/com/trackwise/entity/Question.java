package com.trackwise.entity;

import java.util.List;

import com.trackwise.enums.QuestionNature;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@ManyToMany
	@JoinTable(name = "question_language", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "language_id"))
	private List<Language> languages;

	private boolean internal;

	private QuestionNature questionNature;
}
