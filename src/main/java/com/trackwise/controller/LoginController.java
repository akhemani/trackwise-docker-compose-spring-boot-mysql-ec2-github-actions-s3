package com.trackwise.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trackwise.dto.LoginRequest;
import com.trackwise.entity.User;
import com.trackwise.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class LoginController {

	@Autowired
	private UserRepository userRepository;

	@Operation(summary = "Authenticate user and return user details if credentials are valid")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Login successful"),
			@ApiResponse(responseCode = "401", description = "Invalid username or password") })
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			if (user.getPassword().equals(loginRequest.getPassword())) {
				return ResponseEntity.ok(user); // returns ResponseEntity<User>
			}
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	}
}
