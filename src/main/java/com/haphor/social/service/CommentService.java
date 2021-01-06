package com.haphor.social.service;

import com.haphor.social.dto.request.AddOrUpdateCommentRequestDTO;
import com.haphor.social.dto.response.AllCommentsResponseDTO;
import com.haphor.social.dto.response.CommentResponseDTO;
import com.haphor.social.dto.response.DeleteCommentResponseDTO;

public interface CommentService {
	
	public AllCommentsResponseDTO getCommentsByPostId(int postID);
	
	public CommentResponseDTO addOrUpdateComment(AddOrUpdateCommentRequestDTO addOrUpdateCommentRequestDTO);
	
	public DeleteCommentResponseDTO deleteComment(int commentId);

}
