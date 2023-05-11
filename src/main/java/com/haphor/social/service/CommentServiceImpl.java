package com.haphor.social.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.haphor.social.dao.CommentDAO;
import com.haphor.social.dao.PostDAO;
import com.haphor.social.dto.CommentDTO;
import com.haphor.social.dto.LikeDTO;
import com.haphor.social.dto.request.AddOrUpdateCommentRequestDTO;
import com.haphor.social.dto.response.AllCommentsResponseDTO;
import com.haphor.social.dto.response.CommentResponseDTO;
import com.haphor.social.dto.response.DeleteCommentResponseDTO;
import com.haphor.social.model.Comment;
import com.haphor.social.model.Post;
import com.haphor.social.model.User;
import com.haphor.social.util.constants.NotificationAction;
import com.haphor.social.util.converter.entity.ConvertEntity;
import com.haphor.social.util.jwt.AccessToken;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private AccessToken accessToken;
	
	@Autowired
	private PostDAO postDao;
	
	@Autowired
	private CommentDAO commentDao;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private ConvertEntity convertEntity;
	
	@Override
	public CommentResponseDTO addOrUpdateComment(AddOrUpdateCommentRequestDTO addOrUpdateCommentRequestDTO) {
		
		User user = accessToken.getUserFromToken();
		
		if(user.getUserId() != addOrUpdateCommentRequestDTO.getUserId()) {
			return new CommentResponseDTO(new CommentDTO(), false, "User ID does not match the currently logged in user", HttpStatus.UNAUTHORIZED);
		}
		
		// Get the comment by comment Id
		Optional<Comment> maybeComment = commentDao.findById(addOrUpdateCommentRequestDTO.getCommentId());
		
		// Check if DTO has a commendId.
		if(maybeComment.isPresent()) {
			
			Comment comment = maybeComment.get();
			
			if(comment.getUser().getUserId() != addOrUpdateCommentRequestDTO.getUserId()) {
				return new CommentResponseDTO(new CommentDTO(), false, "You are not authorize to update this comment", HttpStatus.UNAUTHORIZED);
			}
			
			comment.setContent(addOrUpdateCommentRequestDTO.getContent());
			
			// Update the comment
			
			Comment updatedComment = commentDao.save(comment);
			
			Set<LikeDTO> likesDto = convertEntity.toLikeDTOs(updatedComment.getLikes());
			
			CommentDTO commentDto = new CommentDTO(updatedComment.getPost().getPostId(), addOrUpdateCommentRequestDTO.getUserId(),
					updatedComment.getCommentId(), updatedComment.getContent(), likesDto, updatedComment.getCreatedAt(), updatedComment.getUpdatedAt());
			
			
			return new CommentResponseDTO(commentDto, true, "Comment successfully updated", HttpStatus.OK);
	
		}
		
		
		// New comment is yet to be added
		
		// Get the post
		Optional<Post> maybePost = postDao.findById(addOrUpdateCommentRequestDTO.getPostId());
		
		// Check if post exist
		if(maybePost.isPresent()) {
			
			Post post = maybePost.get();
			
			Comment newComment = new Comment();
			
			newComment.setContent(addOrUpdateCommentRequestDTO.getContent());
			newComment.setUser(user);
			newComment.setPost(post);
			
			Comment savedComment = commentDao.save(newComment);
			
			
			CommentDTO commentDto = new CommentDTO(savedComment.getPost().getPostId(), addOrUpdateCommentRequestDTO.getUserId(),
					savedComment.getCommentId(), savedComment.getContent(), new HashSet<>(), savedComment.getCreatedAt(), savedComment.getUpdatedAt());
			
			// Add Notification
			notificationService.addNotification(NotificationAction.COMMENTED, newComment.getUser(), post.getUser(), post, newComment, null, null);
			
//			int byUser = newComment.getUser().getUserId();
//			int forUser = post.getUser().getUserId();
//			
//			// make sure that the user adding the Comment is not the owner of the Post
//			if(byUser != forUser) {
//				
//				// Create notification
//				Notification notification = new Notification();
//				
//				notification.setAction(NotificationAction.COMMENTED);
//				notification.setByUser(newComment.getUser());
//				notification.setForUser(post.getUser());
//				notification.setComment(newComment);
//				notification.setPost(post);
//				
//				// Save the notification
//				notificationDao.save(notification);
//			}
			
			return new CommentResponseDTO(commentDto, true, "Comment successfully saved", HttpStatus.OK);
		}
		
		return new CommentResponseDTO(new CommentDTO(), false, "Post not found", HttpStatus.NOT_FOUND);
	}

	@Override
	public DeleteCommentResponseDTO deleteComment(int commentId) {
		
		User user = accessToken.getUserFromToken();
		
		Optional<Comment> maybeComment = commentDao.findById(commentId);
		
		if(maybeComment.isPresent()) {
			
			Comment comment = maybeComment.get();
			
			if(user.getUserId() != comment.getUser().getUserId()) {
				return new DeleteCommentResponseDTO(commentId, false, "You are not authorized to delete this comment.", HttpStatus.UNAUTHORIZED);
			}
			
			commentDao.deleteById(commentId);
			
			return new DeleteCommentResponseDTO(commentId, true, "Comment successfully deleted.", HttpStatus.OK);
			
		}
		
		return new DeleteCommentResponseDTO(commentId, false, "Comment not found.", HttpStatus.NOT_FOUND);
	}

	@Override
	public AllCommentsResponseDTO getCommentsByPostId(int postId) {
		
		Set<Comment> comments = commentDao.findByPostPostId(postId);
		
		Set<LikeDTO> likesDto = new HashSet<>();
		
		Set<CommentDTO> commentsDto = new HashSet<>();
		
		for(Comment comment : comments) {
			
			likesDto = convertEntity.toLikeDTOs(comment.getLikes());
			
			commentsDto.add(new CommentDTO(comment.getPost().getPostId(), comment.getUser().getUserId(), comment.getCommentId(),
					comment.getContent(), likesDto, comment.getCreatedAt(), comment.getUpdatedAt()));
			
			likesDto = new HashSet<>();
			
		}
		
		Optional<Post> post = postDao.findById(postId);
		
		int userId = 0;
		
		if(post.isPresent()) {
			userId = post.get().getUser().getUserId();
		}
		
		return new AllCommentsResponseDTO(userId, postId, commentsDto, true, "Comments have successfully fetched", HttpStatus.OK);
	}
}
