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

import com.trackwise.entity.Client;
import com.trackwise.repository.ClientRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/clients")
@CrossOrigin(origins = "*") // Angular app origin
public class ClientController {

	@Autowired
	private ClientRepository clientRepo;

	@Operation(summary = "Get all clients")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved client list") })
	@GetMapping
	public List<Client> getAll() {
		return clientRepo.findAll();
	}

	@Operation(summary = "Create a new client")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully created client"),
			@ApiResponse(responseCode = "400", description = "Invalid client data") })
	@PostMapping
	public Client create(@RequestBody Client client) {
		return clientRepo.save(client);
	}

	@Operation(summary = "Update an existing client by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully updated client"),
			@ApiResponse(responseCode = "404", description = "Client not found") })
	@PutMapping("/{id}")
	public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client updated) {
		return clientRepo.findById(id).map(client -> {
			client.setName(updated.getName());
			return ResponseEntity.ok(clientRepo.save(client));
		}).orElse(ResponseEntity.notFound().build());
	}

	@Operation(summary = "Delete a client by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Successfully deleted client"),
			@ApiResponse(responseCode = "404", description = "Client not found") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		if (!clientRepo.existsById(id))
			return ResponseEntity.notFound().build();
		clientRepo.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
