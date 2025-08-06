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
import org.springframework.web.bind.annotation.RestController;

import com.trackwise.dto.AssessmentRequestResponseDTO;
import com.trackwise.service.AssessmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/assessments")
@CrossOrigin(origins = "*")
@Tag(name = "Assessment Controller", description = "CRUD operations for Assessments")
public class AssessmentController {

	@Autowired
	private AssessmentService assessmentService;

	@GetMapping
	@Operation(summary = "Get all assessments")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved assessment list") })
	public ResponseEntity<List<AssessmentRequestResponseDTO>> getAllAssessments() {
		return ResponseEntity.ok(assessmentService.getAllAssessments());
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get assessment by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved assessment"),
			@ApiResponse(responseCode = "404", description = "Assessment not found") })
	public ResponseEntity<AssessmentRequestResponseDTO> getAssessmentById(@PathVariable Long id) {
		return ResponseEntity.ok(assessmentService.getAssessmentById(id));
	}

	@PostMapping
	@Operation(summary = "Create a new assessment")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Assessment created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input provided") })
	public ResponseEntity<AssessmentRequestResponseDTO> createAssessment(
			@RequestBody AssessmentRequestResponseDTO dto) {
		return ResponseEntity.ok(assessmentService.createAssessment(dto));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update an existing assessment")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Assessment updated successfully"),
			@ApiResponse(responseCode = "404", description = "Assessment not found"),
			@ApiResponse(responseCode = "400", description = "Invalid input provided") })
	public ResponseEntity<AssessmentRequestResponseDTO> updateAssessment(@PathVariable Long id,
			@RequestBody AssessmentRequestResponseDTO dto) {
		return ResponseEntity.ok(assessmentService.updateAssessment(id, dto));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete assessment by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Assessment deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Assessment not found") })
	public ResponseEntity<Void> deleteAssessment(@PathVariable Long id) {
		assessmentService.deleteAssessment(id);
		return ResponseEntity.noContent().build();
	}
}
