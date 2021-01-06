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

import com.haphor.social.dto.request.AddOrUpdateReplyRequestDTO;
import com.haphor.social.dto.response.AllRepliesResponseDTO;
import com.haphor.social.dto.response.DeleteReplyResponseDTO;
import com.haphor.social.dto.response.ReplyResponseDTO;
import com.haphor.social.service.ReplyService;
import com.haphor.social.validation.InputValidatorService;

@RestController
@RequestMapping("/api/reply")
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;
	
	@Autowired
	private InputValidatorService inputValidatorService;
	
	@GetMapping("/comment/{commentId}")
	public ResponseEntity<?> getRepliesByCommentId(@PathVariable int commentId){
		
		AllRepliesResponseDTO response = replyService.getReplyByCommentId(commentId);
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);

	}
	
	@PostMapping("/")
	public ResponseEntity<?> saveOrUpdateCOmment(@RequestBody AddOrUpdateReplyRequestDTO addOrUpdateReplyRequestDTO) {
		
		Map<String, Object> message = inputValidatorService.validateReplyInput(addOrUpdateReplyRequestDTO);
		
		// Check if there is an invalid input from client
		if (!message.isEmpty()) {
			message.put("success", false);
			return ResponseEntity.status(422).body(message);
		}
		
		ReplyResponseDTO response = replyService.addOrUpdateReply(addOrUpdateReplyRequestDTO);

		return ResponseEntity.status(response.getHttpStatus()).body(response);

	}
	
	@DeleteMapping("/{replyId}")
	public ResponseEntity<?> saveOrUpdateCOmment(@PathVariable int replyId) {
		
		DeleteReplyResponseDTO response = replyService.deleteReply(replyId);
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);

	}

}
