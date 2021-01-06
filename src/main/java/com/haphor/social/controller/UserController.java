package com.haphor.social.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haphor.social.dto.jwt.UserJwtDTO;
import com.haphor.social.dto.request.AddTechnologiesToUserRequestDTO;
import com.haphor.social.dto.request.AddTitlesToUserRequestDTO;
import com.haphor.social.dto.request.AuthenticationRequestDTO;
import com.haphor.social.dto.request.DeleteTechnologiesFromUserRequestDTO;
import com.haphor.social.dto.request.DeleteTitlesFromUserRequestDTO;
import com.haphor.social.dto.request.UserPasswordUpdateRequestDTO;
import com.haphor.social.dto.request.UserRegistrationRequestDTO;
import com.haphor.social.dto.response.AllUsersResponseDTO;
import com.haphor.social.dto.response.AuthenticationResponseDTO;
import com.haphor.social.dto.response.UserTechnologiesResponseDTO;
import com.haphor.social.dto.response.UserTitlesResponseDTO;
import com.haphor.social.dto.response.UserRegistrationResponseDTO;
import com.haphor.social.dto.response.UserResponseDTO;
import com.haphor.social.dto.response.UserUpdatePasswordResponseDTO;
import com.haphor.social.model.User;
import com.haphor.social.service.UserService;
import com.haphor.social.util.jwt.JwtUtil;
import com.haphor.social.validation.InputValidatorService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private InputValidatorService inputValidatorService;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequestDTO userRegistrationRequestDto) {

		Map<String, Object> message = inputValidatorService.validateUserRegisterInput(userRegistrationRequestDto);

		// Check if there is an invalid input from client
		if (!message.isEmpty()) {
			message.put("success", false);
			return ResponseEntity.unprocessableEntity().body(message);
		}

		UserRegistrationResponseDTO response = userService.saveUser(userRegistrationRequestDto);

		return ResponseEntity.status(response.getHttpStatus()).body(response);

	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) throws Exception {

		Map<String, Object> message = inputValidatorService.validateUserLoginInput(authenticationRequestDTO);

		// Check if there is an invalid input from client
		if (!message.isEmpty()) {
			message.put("success", false);
			return ResponseEntity.status(422).body(message);
		}

		try {
			System.out.println("loginUser: Line 71");
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword()));
			System.out.println("loginUser: Line 74");
		} catch (BadCredentialsException e) {
			System.out.println("loginUser: Line 76");
			
			AuthenticationResponseDTO response =  new AuthenticationResponseDTO("", false, "Username and password do not match.", HttpStatus.UNAUTHORIZED);
			
			return ResponseEntity.status(response.getHttpStatus()).body(response);
		}

		User user = userService.getUserByUsername(authenticationRequestDTO.getUsername());
		
		UserJwtDTO userJwtDto = new UserJwtDTO(user.getUserId(), user.getUsername(), user.getCreatedAt(), user.getUpdatedAt());
		
		String jwt = jwtTokenUtil.generateToken(userJwtDto);
		
		AuthenticationResponseDTO response =  new AuthenticationResponseDTO(jwt, true, "Successfully logged in.", HttpStatus.OK);
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);

	}
	
	@PutMapping("/password")
	public ResponseEntity<?> updatePassword(@RequestBody UserPasswordUpdateRequestDTO userPasswordUpdateRequestDTO) {
		
		Map<String, Object> message = inputValidatorService.validateUserUpdatePasswordInput(userPasswordUpdateRequestDTO);
		
		// Check if there is an invalid input from client
		if (!message.isEmpty()) {
			message.put("success", false);
			return ResponseEntity.unprocessableEntity().body(message);
		}
		
		UserUpdatePasswordResponseDTO response = userService.updatePassword(userPasswordUpdateRequestDTO);
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllUsers(){
		
		AllUsersResponseDTO response = userService.getAllUsers();
		
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> getUser(@PathVariable int userId){
		
		UserResponseDTO userDto = userService.getUserById(userId);
		
		
		return ResponseEntity.status(userDto.getHttpStatus()).body(userDto);
	}
	
	@PostMapping("/titles")
	public ResponseEntity<?> addTitlesToUser(@RequestBody AddTitlesToUserRequestDTO addTitlesToUserRequestDTO) {
		
		UserTitlesResponseDTO response = userService.addTitlesToUser(addTitlesToUserRequestDTO);
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);
		
	}
	
	@DeleteMapping("/titles")
	public ResponseEntity<?> deleteTitlesFromUser(@RequestBody DeleteTitlesFromUserRequestDTO deleteTitlesFromUserRequestDTO) {
		
		UserTitlesResponseDTO response = userService.deleteTitlesFromUser(deleteTitlesFromUserRequestDTO);
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);
	}
	
	@PostMapping("/technologies")
	public ResponseEntity<?> addTechnologiesToUser(@RequestBody AddTechnologiesToUserRequestDTO addTechnologiesToUserRequestDTO) {
		UserTechnologiesResponseDTO response = userService.addTechnologiesToUser(addTechnologiesToUserRequestDTO);
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);
		
	}
	
	@DeleteMapping("/technologies")
	public ResponseEntity<?> deleteTechnologiesFromUser(@RequestBody DeleteTechnologiesFromUserRequestDTO deleteTechnologiesFromUserRequestDTO) {
		
		UserTechnologiesResponseDTO response = userService.deleteTechnologiesFromUser(deleteTechnologiesFromUserRequestDTO);
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);
		
	}

}
