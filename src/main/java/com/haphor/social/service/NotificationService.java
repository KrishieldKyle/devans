package com.haphor.social.service;

import com.haphor.social.dto.response.AllNotificationsResponseDTO;
import com.haphor.social.model.Comment;
import com.haphor.social.model.Like;
import com.haphor.social.model.Post;
import com.haphor.social.model.Reply;
import com.haphor.social.model.User;
import com.haphor.social.util.constants.NotificationAction;

public interface NotificationService {
	
	public AllNotificationsResponseDTO getNotificationsByUser(int userId);
	public void addNotification(NotificationAction action, User byUser, User forUser, Post post, Comment comment, Reply reply, Like like);

}
