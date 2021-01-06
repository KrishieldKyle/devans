package com.haphor.social.dto.notification;

import java.io.Serializable;

import com.haphor.social.util.constants.NotificationAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int notificationId;
	private int byUserId;
	private String message;
	private NotificationAction action;
}
