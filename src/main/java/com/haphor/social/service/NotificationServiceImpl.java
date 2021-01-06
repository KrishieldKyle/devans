package com.haphor.social.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.haphor.social.dao.NotificationDAO;
import com.haphor.social.dto.notification.CommentLikeNotificationDTO;
import com.haphor.social.dto.notification.CommentNotificationDTO;
import com.haphor.social.dto.notification.NotificationDTO;
import com.haphor.social.dto.notification.PostLikeNotificationDTO;
import com.haphor.social.dto.notification.ReplyLikeNotificationDTO;
import com.haphor.social.dto.notification.ReplyNotificationDTO;
import com.haphor.social.dto.response.AllNotificationsResponseDTO;
import com.haphor.social.model.Notification;
import com.haphor.social.util.constants.NotificationAction;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationDAO notificationDao;

	@Override
	public AllNotificationsResponseDTO getNotificationsByUser(int userId) {
		
		Set<Notification> notifications = notificationDao.findByForUserUserId(userId);
		
		Set<NotificationDTO> notificationDtos = new HashSet<>();
		
		NotificationAction notifAction;
		
		NotificationDTO notificationDto = new NotificationDTO();
		
		for(Notification notification : notifications) {
			
			notifAction = notification.getAction().getAction();
			
			if(notifAction == NotificationAction.COMMENTED) {
				
				notificationDto = new CommentNotificationDTO(notification.getPost().getPostId(), notification.getComment().getCommentId());

			}
			else if(notifAction == NotificationAction.REPLIED) {
				
				notificationDto = new ReplyNotificationDTO(notification.getComment().getCommentId(), notification.getReply().getReplyId());

			}
			else if(notifAction == NotificationAction.POST_LIKED) {
				
				notificationDto = new PostLikeNotificationDTO(notification.getPost().getPostId(), notification.getLike().getLikeId());
				
			}
			else if(notifAction == NotificationAction.COMMENT_LIKED) {
				
				notificationDto = new CommentLikeNotificationDTO(notification.getComment().getCommentId(), notification.getLike().getLikeId());
				
			}
			else {
				
				notificationDto = new ReplyLikeNotificationDTO(notification.getReply().getReplyId(), notification.getLike().getLikeId());
				
			}
			
			notificationDto.setAction(notifAction);
			notificationDto.setByUserId(notification.getByUser().getUserId());
			notificationDto.setMessage(notification.getAction().getMessage());
			notificationDto.setNotificationId(notification.getNotificationId());
			
			notificationDtos.add(notificationDto);
			
			notificationDto = new NotificationDTO();
			
		}
		
		return new AllNotificationsResponseDTO(userId,notificationDtos, true, "Notifications successfully fetched", HttpStatus.OK);
	}

}
