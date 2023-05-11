package com.haphor.social.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.haphor.social.dao.CommentDAO;
import com.haphor.social.dao.ReplyDAO;
import com.haphor.social.dto.LikeDTO;
import com.haphor.social.dto.ReplyDTO;
import com.haphor.social.dto.request.AddOrUpdateReplyRequestDTO;
import com.haphor.social.dto.response.AllRepliesResponseDTO;
import com.haphor.social.dto.response.DeleteReplyResponseDTO;
import com.haphor.social.dto.response.ReplyResponseDTO;
import com.haphor.social.model.Comment;
import com.haphor.social.model.Reply;
import com.haphor.social.model.User;
import com.haphor.social.util.constants.NotificationAction;
import com.haphor.social.util.converter.entity.ConvertEntity;
import com.haphor.social.util.jwt.AccessToken;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	private ReplyDAO replyDao;
	
	@Autowired
	private CommentDAO commentDao;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private AccessToken accessToken;
	
	@Autowired
	private ConvertEntity convertEntity;
	
	@Override
	public ReplyResponseDTO addOrUpdateReply(AddOrUpdateReplyRequestDTO addOrUpdateReplyRequestDTO) {
		User user = accessToken.getUserFromToken();
		
		if(user.getUserId() != addOrUpdateReplyRequestDTO.getUserId()) {
			return new ReplyResponseDTO(new ReplyDTO(), false, "User ID does not match the currently logged in user", HttpStatus.UNAUTHORIZED);
		}
		
		Optional<Reply> maybeReply = replyDao.findById(addOrUpdateReplyRequestDTO.getReplyId());
		
		// Check if a Reply with the replyId given exist
		if(maybeReply.isPresent()) {
			
			// Do update the reply
			Reply reply = maybeReply.get();
			
			// Check if the Reply does no belong to the currently logged in user
			if(reply.getUser().getUserId() != addOrUpdateReplyRequestDTO.getUserId()) {
				return new ReplyResponseDTO(new ReplyDTO(), false, "You are not authorized to update this reply", HttpStatus.UNAUTHORIZED);
			}
			
			reply.setContent(addOrUpdateReplyRequestDTO.getContent());
			
			Reply updatedReply = replyDao.save(reply);
			
			Set<LikeDTO> likesDto = convertEntity.toLikeDTOs(updatedReply.getLikes());
			
			ReplyDTO replyDto = new ReplyDTO(updatedReply.getReplyId(), updatedReply.getComment().getCommentId(), updatedReply.getUser().getUserId(),
					updatedReply.getContent(), likesDto);
			
			
			return new ReplyResponseDTO(replyDto, true, "Reply successfully updated", HttpStatus.OK);
			
		}
		
		// New reply is yet to be added
		
		// Get the comment
		Optional<Comment> maybeComment = commentDao.findById(addOrUpdateReplyRequestDTO.getCommentId());
		
		// Check if comment exist
		if(maybeComment.isPresent()) {
			
			Comment comment = maybeComment.get();
			
			Reply newReply = new Reply();
			
			newReply.setContent(addOrUpdateReplyRequestDTO.getContent());
			newReply.setUser(user);
			newReply.setComment(comment);
			
			Reply savedReply = replyDao.save(newReply);
			
			// Add Notification
			notificationService.addNotification(NotificationAction.REPLIED, newReply.getUser(), comment.getUser(), null, comment, newReply, null);
			
//			int byUser = newReply.getUser().getUserId();
//			int forUser = comment.getUser().getUserId();
//			
//			// make sure that the user adding the Reply is not the owner of the Comment
//			if(byUser != forUser) {
//			
//				// Create a notification
//				Notification notification = new Notification();
//				
//				notification.setAction(NotificationAction.REPLIED);
//				notification.setByUser(newReply.getUser());
//				notification.setForUser(comment.getUser());
//				notification.setComment(comment);
//				notification.setReply(newReply);
//				
//				// Save the notification
//				notificationDao.save(notification);
//			}
			
			ReplyDTO replyDto = new ReplyDTO(savedReply.getReplyId(), savedReply.getComment().getCommentId(), savedReply.getUser().getUserId(),
					savedReply.getContent(), new HashSet<>());
			
			return new ReplyResponseDTO(replyDto, true, "Reply successfully added", HttpStatus.OK);
		}
		
		return new ReplyResponseDTO(new ReplyDTO(), true, "Comment not found.", HttpStatus.NOT_FOUND);
	}

	@Override
	public DeleteReplyResponseDTO deleteReply(int replyId) {
		
		User user = accessToken.getUserFromToken();
		
		Optional<Reply> maybeReply = replyDao.findById(replyId);
		
		if(maybeReply.isPresent()) {
			
			Reply reply = maybeReply.get();
			
			if(user.getUserId() != reply.getUser().getUserId()) {
				new DeleteReplyResponseDTO(replyId, false, "You are not authorized to delete this reply", HttpStatus.UNAUTHORIZED);
			}
			
			replyDao.deleteById(replyId);
			
			return new DeleteReplyResponseDTO(replyId, true, "Reply has successfully deleted.", HttpStatus.OK);
			
		}
		
		return new DeleteReplyResponseDTO(replyId, false, "Reply not found.", HttpStatus.NOT_FOUND);
	}

	@Override
	public AllRepliesResponseDTO getReplyByCommentId(int commentId) {
		
		Set<Reply> replies = replyDao.findByCommentCommentId(commentId);
		
		Set<LikeDTO> likesDto = new HashSet<>();
		
		Set<ReplyDTO> repliesDto = new HashSet<>();
		
		for(Reply reply : replies) {
			
			likesDto = convertEntity.toLikeDTOs(reply.getLikes());
			
			repliesDto.add(new ReplyDTO(reply.getReplyId(), reply.getComment().getCommentId(), reply.getUser().getUserId(),
					reply.getContent(), likesDto));
			
			likesDto = new HashSet<>();
			
		}
		
		int userId = 0;
		
		Optional<Comment> maybeComment = commentDao.findById(commentId);
		
		if(maybeComment.isPresent()) {
			userId = maybeComment.get().getUser().getUserId();
		}
		
		return new AllRepliesResponseDTO(userId, maybeComment.get().getPost().getPostId(), commentId, repliesDto, true, "Replies sucessfully fetched", HttpStatus.OK);
	}

}
