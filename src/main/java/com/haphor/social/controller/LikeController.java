package com.haphor.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haphor.social.dto.response.AddLikeToCommentResponseDTO;
import com.haphor.social.dto.response.AddLikeToPostResponseDTO;
import com.haphor.social.dto.response.AddLikeToReplyResponseDTO;
import com.haphor.social.service.LikeService;

@RestController
@RequestMapping("/api/like")
public class LikeController {
	
	@Autowired
	private LikeService likeService;
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<?> getLikesByPostId(@PathVariable int postId) {

		return ResponseEntity.ok(likeService.getLikesByPostId(postId));

	}
	
	@GetMapping("/comment/{commentId}")
	public ResponseEntity<?> getLikesByCommentId(@PathVariable int commentId) {

		return ResponseEntity.ok(likeService.getLikesByCommentId(commentId));

	}
	
	@GetMapping("/reply/{replyId}")
	public ResponseEntity<?> getLikesByReplyId(@PathVariable int replyId) {

		return ResponseEntity.ok(likeService.getLikesByReplyId(replyId));

	}
	
	@PostMapping("/post/{postId}")
	public ResponseEntity<?> addLikestoPost(@PathVariable int postId) {
		
		AddLikeToPostResponseDTO response = likeService.addLikeToPost(postId);

		return ResponseEntity.status(response.getHttpStatus()).body(response);

	}
	
	@PostMapping("/comment/{commentId}")
	public ResponseEntity<?> addLikestoComment(@PathVariable int commentId) {
		
		AddLikeToCommentResponseDTO response = likeService.addLikeToComment(commentId);

		return ResponseEntity.status(response.getHttpStatus()).body(response);

	}
	
	@PostMapping("/reply/{replyId}")
	public ResponseEntity<?> addLikestoReply(@PathVariable int replyId) {
		
		AddLikeToReplyResponseDTO response = likeService.addLikeToReply(replyId);
	
		return ResponseEntity.status(response.getHttpStatus()).body(response);

	}


}
