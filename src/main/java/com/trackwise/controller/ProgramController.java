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

import com.trackwise.entity.Program;
import com.trackwise.repository.ProgramRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/programs")
@CrossOrigin(origins = "*")
public class ProgramController {

	@Autowired
	private ProgramRepository programRepo;

	@Operation(summary = "Get all programs")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved program list") })
	@GetMapping
	public List<Program> getAll() {
		return programRepo.findAll();
	}

	@Operation(summary = "Create a new program")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully created program"),
			@ApiResponse(responseCode = "400", description = "Invalid program data") })
	@PostMapping
	public Program create(@RequestBody Program program) {
		return programRepo.save(program);
	}

	@Operation(summary = "Update an existing program by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully updated program"),
			@ApiResponse(responseCode = "404", description = "Program not found") })
	@PutMapping("/{id}")
	public ResponseEntity<Program> update(@PathVariable Long id, @RequestBody Program updated) {
		return programRepo.findById(id).map(program -> {
			program.setName(updated.getName());
			return ResponseEntity.ok(programRepo.save(program));
		}).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Delete a program by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successfully deleted program"),
			@ApiResponse(responseCode = "404", description = "Program not found") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!programRepo.existsById(id))
			return ResponseEntity.notFound().build();
		programRepo.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
