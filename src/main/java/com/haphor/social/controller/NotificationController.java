package com.haphor.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.haphor.social.dto.response.AllNotificationsResponseDTO;
import com.haphor.social.service.NotificationService;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
	
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> getNotificationsByUser(@PathVariable int userId){
		
		AllNotificationsResponseDTO response = notificationService.getNotificationsByUser(userId);
		
		
		return ResponseEntity.status(response.getHttpStatus()).body(response);
	}

}
