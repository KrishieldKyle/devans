package com.haphor.social.service;

import com.haphor.social.dto.response.AllNotificationsResponseDTO;

public interface NotificationService {
	
	public AllNotificationsResponseDTO getNotificationsByUser(int userId);

}
