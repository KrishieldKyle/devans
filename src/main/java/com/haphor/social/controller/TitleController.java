package com.haphor.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haphor.social.dto.response.AllTitlesResponseDTO;
import com.haphor.social.dto.response.TitleResponseDTO;
import com.haphor.social.service.TitleService;

@RestController
@RequestMapping("/api/title")
public class TitleController {
	
	@Autowired
	private TitleService titleService;

	@GetMapping("/")
	public ResponseEntity<?> getAllTitles() {
		
		AllTitlesResponseDTO response = titleService.getTitles();

		return ResponseEntity.status(response.getHttpStatus()).body(response);

	}
	
	@GetMapping("/{titleId}")
	public ResponseEntity<?> getTitleById(@PathVariable int titleId) {
		
		TitleResponseDTO response = titleService.getTitle(titleId);

		return ResponseEntity.status(response.getHttpStatus()).body(response);

	}
}
