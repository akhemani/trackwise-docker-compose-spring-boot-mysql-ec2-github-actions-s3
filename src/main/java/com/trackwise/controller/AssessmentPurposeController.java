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

import com.trackwise.entity.AssessmentPurpose;
import com.trackwise.repository.AssessmentPurposeRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/assessment-purposes")
@CrossOrigin(origins = "*")
public class AssessmentPurposeController {

	@Autowired
	private AssessmentPurposeRepository purposeRepo;

	@Operation(summary = "Get all assessment purposes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully fetched all assessment purposes") })
	@GetMapping
	public List<AssessmentPurpose> getAll() {
		return purposeRepo.findAll();
	}

	@Operation(summary = "Create a new assessment purpose")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully created assessment purpose"),
			@ApiResponse(responseCode = "400", description = "Invalid request") })
	@PostMapping
	public AssessmentPurpose create(@RequestBody AssessmentPurpose purpose) {
		return purposeRepo.save(purpose);
	}

	@Operation(summary = "Update an existing assessment purpose")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully updated assessment purpose"),
			@ApiResponse(responseCode = "404", description = "Assessment purpose not found") })
	@PutMapping("/{id}")
	public ResponseEntity<AssessmentPurpose> update(@PathVariable Long id, @RequestBody AssessmentPurpose updated) {
		return purposeRepo.findById(id).map(purpose -> {
			purpose.setName(updated.getName());
			return ResponseEntity.ok(purposeRepo.save(purpose));
		}).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Delete an assessment purpose by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successfully deleted assessment purpose"),
			@ApiResponse(responseCode = "404", description = "Assessment purpose not found") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!purposeRepo.existsById(id))
			return ResponseEntity.notFound().build();
		purposeRepo.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
