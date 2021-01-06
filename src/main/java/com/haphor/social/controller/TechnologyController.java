package com.haphor.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haphor.social.dto.response.AllTechnologiesResponseDTO;
import com.haphor.social.dto.response.TechnologyResponseDTO;
import com.haphor.social.service.TechnologyService;

@RestController
@RequestMapping("/api/technology")
public class TechnologyController {
	
	@Autowired
	private TechnologyService technologyService;

	@GetMapping("/")
	public ResponseEntity<?> registerUser() {
		
		AllTechnologiesResponseDTO response = technologyService.getTechnologies();

		return ResponseEntity.status(response.getHttpStatus()).body(response);

	}
	
	@GetMapping("/{technologyId}")
	public ResponseEntity<?> registerUser(@PathVariable int technologyId) {

		TechnologyResponseDTO response = technologyService.getTechnology(technologyId);
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);

	}
}
