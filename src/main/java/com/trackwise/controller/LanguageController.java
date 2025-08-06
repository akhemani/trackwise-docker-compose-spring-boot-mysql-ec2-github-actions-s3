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

import com.trackwise.entity.Language;
import com.trackwise.repository.LanguageRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/languages")
@CrossOrigin(origins = "*")
public class LanguageController {

	@Autowired
	private LanguageRepository languageRepo;

	@Operation(summary = "Get all programming languages")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved language list") })
	@GetMapping
	public List<Language> getAll() {
		return languageRepo.findAll();
	}

	@Operation(summary = "Create a new programming language")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully created language"),
			@ApiResponse(responseCode = "400", description = "Invalid language data") })
	@PostMapping
	public Language create(@RequestBody Language language) {
		return languageRepo.save(language);
	}

	@Operation(summary = "Update an existing programming language by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully updated language"),
			@ApiResponse(responseCode = "404", description = "Language not found") })
	@PutMapping("/{id}")
	public ResponseEntity<Language> update(@PathVariable Long id, @RequestBody Language updated) {
		return languageRepo.findById(id).map(language -> {
			language.setName(updated.getName());
			return ResponseEntity.ok(languageRepo.save(language));
		}).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Delete a programming language by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successfully deleted language"),
			@ApiResponse(responseCode = "404", description = "Language not found") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!languageRepo.existsById(id))
			return ResponseEntity.notFound().build();
		languageRepo.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
