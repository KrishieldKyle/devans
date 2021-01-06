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

import com.haphor.social.dto.request.AddOrUpdateCommentRequestDTO;
import com.haphor.social.dto.response.AllCommentsResponseDTO;
import com.haphor.social.dto.response.CommentResponseDTO;
import com.haphor.social.dto.response.DeleteCommentResponseDTO;
import com.haphor.social.service.CommentService;
import com.haphor.social.validation.InputValidatorService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private InputValidatorService inputValidatorService;
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<?> getCommentsByPostId(@PathVariable int postId) {
		
		AllCommentsResponseDTO response = commentService.getCommentsByPostId(postId);

		return ResponseEntity.status(response.getHttpStatus()).body(response);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> saveOrUpdateComment(@RequestBody AddOrUpdateCommentRequestDTO addOrUpdateCommentRequestDTO) {
		
		Map<String, Object> message = inputValidatorService.validateCommentInput(addOrUpdateCommentRequestDTO);
		
		// Check if there is an invalid input from client
		if (!message.isEmpty()) {
			message.put("success", false);
			return ResponseEntity.status(422).body(message);
		}
		
		CommentResponseDTO response = commentService.addOrUpdateComment(addOrUpdateCommentRequestDTO);

		return ResponseEntity.status(response.getHttpStatus()).body(response);

	}
	
	@DeleteMapping("/{commentId}")
	public ResponseEntity<?> deleteComment(@PathVariable int commentId) {
		
		DeleteCommentResponseDTO response = commentService.deleteComment(commentId);
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);

	}

}
