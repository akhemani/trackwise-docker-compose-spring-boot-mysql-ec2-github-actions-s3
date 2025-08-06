package com.trackwise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trackwise.dto.QuestionRequestResponseDTO;
import com.trackwise.enums.QuestionNature;
import com.trackwise.service.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
@Tag(name = "Question Controller", description = "CRUD operations for Questions")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@GetMapping
	@Operation(summary = "Get all questions")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved question list") })
	public ResponseEntity<List<QuestionRequestResponseDTO>> getAllQuestions() {
		return ResponseEntity.ok(questionService.getAllQuestions());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get question by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved the question"),
			@ApiResponse(responseCode = "404", description = "Question not found with given ID") })
	public ResponseEntity<QuestionRequestResponseDTO> getQuestionById(@PathVariable Long id) {
		return ResponseEntity.ok(questionService.getQuestionById(id));
	}

	@PostMapping
	@Operation(summary = "Create a new question")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Question created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input provided") })
	public ResponseEntity<QuestionRequestResponseDTO> createQuestion(@RequestBody QuestionRequestResponseDTO dto) {
		return ResponseEntity.ok(questionService.createQuestion(dto));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update an existing question")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Question updated successfully"),
			@ApiResponse(responseCode = "404", description = "Question not found with given ID"),
			@ApiResponse(responseCode = "400", description = "Invalid input provided") })
	public ResponseEntity<QuestionRequestResponseDTO> updateQuestion(@PathVariable Long id,
			@RequestBody QuestionRequestResponseDTO dto) {
		return ResponseEntity.ok(questionService.updateQuestion(id, dto));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a question by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Question deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Question not found with given ID") })
	public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
		questionService.deleteQuestion(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/by-nature")
	@Operation(summary = "Get questions by nature", description = "Fetch all questions that match the provided QuestionNature enum value")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Questions fetched successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid QuestionNature value provided"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<List<QuestionRequestResponseDTO>> getQuestionsByNature(
			@RequestParam("type") QuestionNature nature) {

		List<QuestionRequestResponseDTO> result = questionService.getQuestionsByNature(nature);
		return ResponseEntity.ok(result);
	}
}
