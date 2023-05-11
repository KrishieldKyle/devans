package com.haphor.social.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.haphor.social.dao.CommentDAO;
import com.haphor.social.dao.LikeDAO;
import com.haphor.social.dao.PostDAO;
import com.haphor.social.dao.ReplyDAO;
import com.haphor.social.dto.LikeDTO;
import com.haphor.social.dto.response.AddLikeToCommentResponseDTO;
import com.haphor.social.dto.response.AddLikeToPostResponseDTO;
import com.haphor.social.dto.response.AddLikeToReplyResponseDTO;
import com.haphor.social.dto.response.CommentLikesResponseDTO;
import com.haphor.social.dto.response.PostLikesResponseDTO;
import com.haphor.social.dto.response.ReplyLikesResponseDTO;
import com.haphor.social.model.Comment;
import com.haphor.social.model.Like;
import com.haphor.social.model.Post;
import com.haphor.social.model.Reply;
import com.haphor.social.model.User;
import com.haphor.social.util.constants.NotificationAction;
import com.haphor.social.util.converter.entity.ConvertEntity;
import com.haphor.social.util.jwt.AccessToken;

@Service
public class LikeServiceImpl implements LikeService {
	
	@Autowired
	private LikeDAO likeDao;
	
	@Autowired
	private PostDAO postDao;
	
	@Autowired
	private CommentDAO commentDao;
	
	@Autowired
	private ReplyDAO replyDao;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private AccessToken access;
	
	@Autowired
	private ConvertEntity convertEntity;

	@Override
	public PostLikesResponseDTO getLikesByPostId(int postId) {
		
		Set<Like> likes = likeDao.findByPostPostId(postId);
		
		Set<LikeDTO> likesDto = convertEntity.toLikeDTOs(likes);
		
		Optional<Post> maybePost = postDao.findById(postId);
		
		int userId = 0;
		
		if(maybePost.isPresent()) {
			userId = maybePost.get().getUser().getUserId();
		}
		
		return new PostLikesResponseDTO(postId, userId, likesDto, true, "Likes successfully fetched.", HttpStatus.OK);
	}

	@Override
	public CommentLikesResponseDTO getLikesByCommentId(int commentId) {
		
		Set<Like> likes = likeDao.findByCommentCommentId(commentId);
		
		Set<LikeDTO> likesDto = convertEntity.toLikeDTOs(likes);
		
		Optional<Comment> maybeComment = commentDao.findById(commentId);
		
		int userId = 0;
		
		if(maybeComment.isPresent()) {
			userId = maybeComment.get().getUser().getUserId();
		}
		
		return new CommentLikesResponseDTO(commentId, userId, likesDto, true, "Likes successfully fetched.", HttpStatus.OK);
	}

	@Override
	public ReplyLikesResponseDTO getLikesByReplyId(int replyId) {
		Set<Like> likes = likeDao.findByReplyReplyId(replyId);
		
		Set<LikeDTO> likesDto = convertEntity.toLikeDTOs(likes);
		
		Optional<Reply> maybeReply = replyDao.findById(replyId);
		
		int userId = 0;
		
		if(maybeReply.isPresent()) {
			userId = maybeReply.get().getUser().getUserId();
		}
		
		return new ReplyLikesResponseDTO(replyId, userId, likesDto, true, "Likes successfully fetched.", HttpStatus.OK);
	}

	@Override
	public AddLikeToPostResponseDTO addLikeToPost(int postId) {
		User user = access.getUserFromToken();
		
		Optional<Post> maybePost = postDao.findById(postId);
		
		if(maybePost.isPresent()) {
			
			Post post = maybePost.get();
			
			Optional<Like> maybeLike = likeDao.findByPostPostIdAndUserUserId(postId, user.getUserId());
			
			if(maybeLike.isPresent()) {
				
				Like like = maybeLike.get();
				
				// Remove like
				likeDao.deleteById(like.getLikeId());
				
				return new AddLikeToPostResponseDTO(like.getLikeId(), postId, true, "Post unliked.", HttpStatus.OK);
			}
			
			Like like = new Like();
			
			like.setPost(post);
			
			like.setUser(user);
			
			// Add Like
			Like newLike = likeDao.save(like);
			
			// Add Notification
			notificationService.addNotification(NotificationAction.POST_LIKED, newLike.getUser(), post.getUser(), post, null, null, newLike);
			
//			// Create a notification
//			Notification notification = new Notification();
//			notification.setAction(NotificationAction.POST_LIKED);
//			notification.setByUser(newLike.getUser());
//			notification.setForUser(post.getUser());
//			notification.setLike(newLike);
//			notification.setPost(post);
//			
//			// Save the notification
//			notificationDao.save(notification);
			
			return new AddLikeToPostResponseDTO(newLike.getLikeId(), postId, true, "Post liked.", HttpStatus.OK);
		}
		
		return new AddLikeToPostResponseDTO(0, postId, false, "Post not found.", HttpStatus.NOT_FOUND);
	}

	@Override
	public AddLikeToCommentResponseDTO addLikeToComment(int commentId) {
		User user = access.getUserFromToken();
		
		Optional<Comment> maybeComment = commentDao.findById(commentId);
		
		if(maybeComment.isPresent()) {
			
			Comment comment = maybeComment.get();
			
			Optional<Like> maybeLike = likeDao.findByCommentCommentIdAndUserUserId(commentId, user.getUserId());
			
			if(maybeLike.isPresent()) {
				
				Like like = maybeLike.get();
				
				likeDao.deleteById(like.getLikeId());
				
				return new AddLikeToCommentResponseDTO(maybeLike.get().getLikeId(), commentId, true, "Comment unliked.", HttpStatus.OK);
			}
			
			Like like = new Like();
			
			like.setComment(comment);
			
			like.setUser(user);
			
			Like newLike = likeDao.save(like);
			
			// Add Notification
			notificationService.addNotification(NotificationAction.COMMENT_LIKED, newLike.getUser(), comment.getUser(), null, comment, null, newLike);
			
//			// Create a notification
//			Notification notification = new Notification();
//			notification.setAction(NotificationAction.COMMENT_LIKED);
//			notification.setByUser(newLike.getUser());
//			notification.setForUser(comment.getUser());
//			notification.setLike(newLike);
//			notification.setComment(comment);
//			
//			// Save the notification
//			notificationDao.save(notification);
			
			return new AddLikeToCommentResponseDTO(newLike.getLikeId(), commentId, true, "Comment liked.", HttpStatus.OK);
		}
		return new AddLikeToCommentResponseDTO(0, commentId, false, "Comment not found.", HttpStatus.NOT_FOUND);
	}

	@Override
	public AddLikeToReplyResponseDTO addLikeToReply(int replyId) {
		User user = access.getUserFromToken();
		
		Optional<Reply> maybeReply = replyDao.findById(replyId);
		
		if(maybeReply.isPresent()) {
			
			Reply reply = maybeReply.get();
			
			Optional<Like> maybeLike = likeDao.findByReplyReplyIdAndUserUserId(replyId, user.getUserId());
			
			if(maybeLike.isPresent()) {
				
				Like like = maybeLike.get();
				
				likeDao.deleteById(like.getLikeId());
				
				return new AddLikeToReplyResponseDTO(maybeLike.get().getLikeId(), replyId, true, "Reply unliked.", HttpStatus.OK);
			}
			
			Like like = new Like();
			
			like.setReply(reply);
			
			like.setUser(user);
			
			Like newLike = likeDao.save(like);
			
			// Add Notification
			notificationService.addNotification(NotificationAction.REPLY_LIKED, newLike.getUser(), reply.getUser(), null, null, reply, newLike);
			
//			// Create a notification
//			Notification notification = new Notification();
//			notification.setAction(NotificationAction.REPLY_LIKED);
//			notification.setByUser(newLike.getUser());
//			notification.setForUser(reply.getUser());
//			notification.setLike(newLike);
//			notification.setReply(reply);
//			
//			// Save the notification
//			notificationDao.save(notification);
			
			return new AddLikeToReplyResponseDTO(newLike.getLikeId(), replyId, true, "Reply liked.", HttpStatus.OK);
		}
		return new AddLikeToReplyResponseDTO(0, replyId, false, "Reply not found.", HttpStatus.NOT_FOUND);
	}
}
