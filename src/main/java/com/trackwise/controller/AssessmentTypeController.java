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

import com.trackwise.entity.AssessmentType;
import com.trackwise.repository.AssessmentTypeRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/assessment-type")
@CrossOrigin(origins = "*")
public class AssessmentTypeController {

	@Autowired
	private AssessmentTypeRepository assessmentTypeRepo;

	@Operation(summary = "Get all assessment types")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved assessment type list") })
	@GetMapping
	public List<AssessmentType> getAll() {
		return assessmentTypeRepo.findAll();
	}

	@Operation(summary = "Create a new AssessmentType")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully created an AssessmentType"),
			@ApiResponse(responseCode = "400", description = "Invalid AssessmentType data") })
	@PostMapping
	public AssessmentType create(@RequestBody AssessmentType AssessmentType) {
		return assessmentTypeRepo.save(AssessmentType);
	}

	@Operation(summary = "Update an existing AssessmentType by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully updated an AssessmentType"),
			@ApiResponse(responseCode = "404", description = "AssessmentType not found") })
	@PutMapping("/{id}")
	public ResponseEntity<AssessmentType> update(@PathVariable Long id, @RequestBody AssessmentType updated) {
		return assessmentTypeRepo.findById(id).map(AssessmentType -> {
			AssessmentType.setName(updated.getName());
			return ResponseEntity.ok(assessmentTypeRepo.save(AssessmentType));
		}).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Delete an AssessmentType by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successfully deleted AssessmentType"),
			@ApiResponse(responseCode = "404", description = "AssessmentType not found") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!assessmentTypeRepo.existsById(id))
			return ResponseEntity.notFound().build();
		assessmentTypeRepo.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
