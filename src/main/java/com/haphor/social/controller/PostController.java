package com.haphor.social.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haphor.social.dto.request.AddOrUpdatePostRequestDTO;
import com.haphor.social.dto.request.AddTechnologiesToPostRequestDTO;
import com.haphor.social.dto.request.DeleteTechnologiesFromPostRequestDTO;
import com.haphor.social.dto.response.AllPostsResponseDTO;
import com.haphor.social.dto.response.DeletePostResponseDTO;
import com.haphor.social.dto.response.PostResponseDTO;
import com.haphor.social.dto.response.PostTechnologyResponseDTO;
import com.haphor.social.service.PostService;
import com.haphor.social.validation.InputValidatorService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private InputValidatorService inputValidatorService;
	
	@GetMapping("/")
	public ResponseEntity<?> getAllPost() {
		
		AllPostsResponseDTO allPostResponseDTO = postService.getAllPost();

		return ResponseEntity.status(allPostResponseDTO.getHttpStatus()).body(allPostResponseDTO);

	}
	
	@GetMapping("/{postId}")
	public ResponseEntity<?> getPost(@PathVariable int postId) {
		
		PostResponseDTO postResponseDTO = postService.getPost(postId);

		return ResponseEntity.status(postResponseDTO.getHttpStatus()).body(postResponseDTO);

	}
	
	@PostMapping("/")
	public ResponseEntity<?> saveOrUpdatePost(@RequestBody AddOrUpdatePostRequestDTO addOrUpdatePostRequestDTO) {
		
		Map<String, Object> message = inputValidatorService.validatePostInput(addOrUpdatePostRequestDTO);
		
		// Check if there is an invalid input from client
		if (!message.isEmpty()) {
			message.put("success", false);
			return ResponseEntity.status(422).body(message);
		}
		
		PostResponseDTO postResponseDTO = postService.saveOrUpdatePost(addOrUpdatePostRequestDTO);

		return ResponseEntity.status(postResponseDTO.getHttpStatus()).body(postResponseDTO);

	}
	
	@DeleteMapping("/{postId}")
	public ResponseEntity<?> deletePostById(@PathVariable int postId) {
		
		DeletePostResponseDTO deletePostResponseDTO = postService.deletePost(postId);

		return ResponseEntity.status(deletePostResponseDTO.getHttpStatus()).body(deletePostResponseDTO);

	}
	
	@PostMapping("/technologies")
	public ResponseEntity<?> addTechnologiesToPost(@RequestBody AddTechnologiesToPostRequestDTO addTechnologiesToPostRequestDTO) {
		
		Map<String, Object> message = inputValidatorService.validatePostTechnologyInput(addTechnologiesToPostRequestDTO);
		
		// Check if there is an invalid input from client
		if (!message.isEmpty()) {
			message.put("success", false);
			return ResponseEntity.status(422).body(message);
		}
		
		PostTechnologyResponseDTO response = postService.addTechnologiesToPost(addTechnologiesToPostRequestDTO);
		
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);
		
	}
	
	@DeleteMapping("/technologies")
	public ResponseEntity<?> deleteTechnologiesFromPost(@RequestBody DeleteTechnologiesFromPostRequestDTO deleteTechnologiesFromPostRequestDTO) {
		Map<String, Object> message = inputValidatorService.validatePostTechnologyInput(deleteTechnologiesFromPostRequestDTO);
		
		// Check if there is an invalid input from client
		if (!message.isEmpty()) {
			message.put("success", false);
			return ResponseEntity.status(422).body(message);
		}
		
		PostTechnologyResponseDTO response = postService.deleteTechnologiesFromPost(deleteTechnologiesFromPostRequestDTO);
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);
		
	}

}
