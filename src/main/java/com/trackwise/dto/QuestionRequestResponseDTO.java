package com.trackwise.dto;

import java.util.List;

@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Getter
@lombok.Setter
@lombok.ToString
public class QuestionRequestResponseDTO {
	private Long id;
	private boolean internal;
	private String title;
	private List<String> languages;
	private String questionNature;
}
