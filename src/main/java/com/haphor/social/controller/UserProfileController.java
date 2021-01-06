package com.haphor.social.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haphor.social.dto.request.AddOrUpdateUserProfileRequestDTO;
import com.haphor.social.dto.response.AddOrUpdateUserProfileResponseDTO;
import com.haphor.social.service.UserProfileService;
import com.haphor.social.validation.InputValidatorService;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {
	
	@Autowired
	private InputValidatorService inputValidatorService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@PostMapping("/")
	public ResponseEntity<?> addOrUpdateUserProfile(@RequestBody AddOrUpdateUserProfileRequestDTO AddOrUpdateUserProfileRequestDTO) {
		
		Map<String, Object> message = inputValidatorService.validateUserProfileInput(AddOrUpdateUserProfileRequestDTO);
		
		// Check if there is an invalid input from client
		if (!message.isEmpty()) {
			message.put("success", false);
			return ResponseEntity.status(422).body(message);
		}
		
		AddOrUpdateUserProfileResponseDTO addOrUpdateUserProfileResponseDTO = userProfileService.saveOrUpdateUserProfile(AddOrUpdateUserProfileRequestDTO);
		
		return ResponseEntity.status(addOrUpdateUserProfileResponseDTO.getHttpStatus()).body(addOrUpdateUserProfileResponseDTO);
		
	}

}
