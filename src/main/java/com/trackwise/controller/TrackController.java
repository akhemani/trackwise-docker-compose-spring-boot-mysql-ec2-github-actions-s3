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

import com.trackwise.entity.Track;
import com.trackwise.repository.TrackRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/tracks")
@CrossOrigin(origins = "*")
public class TrackController {

	@Autowired
	private TrackRepository trackRepo;

	@Operation(summary = "Get all tracks")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved track list") })
	@GetMapping
	public List<Track> getAll() {
		return trackRepo.findAll();
	}

	@Operation(summary = "Create a new track")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully created track"),
			@ApiResponse(responseCode = "400", description = "Invalid track data") })
	@PostMapping
	public Track create(@RequestBody Track track) {
		return trackRepo.save(track);
	}

	@Operation(summary = "Update an existing track by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully updated track"),
			@ApiResponse(responseCode = "404", description = "Track not found") })
	@PutMapping("/{id}")
	public ResponseEntity<Track> update(@PathVariable Long id, @RequestBody Track updated) {
		return trackRepo.findById(id).map(track -> {
			track.setName(updated.getName());
			return ResponseEntity.ok(trackRepo.save(track));
		}).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Delete a track by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successfully deleted track"),
			@ApiResponse(responseCode = "404", description = "Track not found") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!trackRepo.existsById(id))
			return ResponseEntity.notFound().build();
		trackRepo.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
