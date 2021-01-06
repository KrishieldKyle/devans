package com.haphor.social.service;

import com.haphor.social.dto.response.AddLikeToCommentResponseDTO;
import com.haphor.social.dto.response.AddLikeToPostResponseDTO;
import com.haphor.social.dto.response.AddLikeToReplyResponseDTO;
import com.haphor.social.dto.response.CommentLikesResponseDTO;
import com.haphor.social.dto.response.PostLikesResponseDTO;
import com.haphor.social.dto.response.ReplyLikesResponseDTO;

public interface LikeService {
	
	public PostLikesResponseDTO getLikesByPostId(int postId);
	
	public CommentLikesResponseDTO getLikesByCommentId(int commentId);
	
	public ReplyLikesResponseDTO getLikesByReplyId(int replyId);
	
//	public Like getLikeByPostPostIdAndUserUserId(int postId, int userId);
//	
//	public Like getLikeByCommentCommentIdAndUserUserId(int commentId, int userId);
//	
//	public Like getLikeByReplyReplyIdAndUserUserId(int replyId, int userId);
	
	public AddLikeToPostResponseDTO addLikeToPost(int postId);
	
	public AddLikeToCommentResponseDTO addLikeToComment(int commentId);
	
	public AddLikeToReplyResponseDTO addLikeToReply(int replyId);
	
//	public int deleteLike(int likeId);

}
