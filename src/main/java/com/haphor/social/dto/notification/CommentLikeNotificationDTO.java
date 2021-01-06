package com.haphor.social.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeNotificationDTO extends NotificationDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int commentId;
	private int likeId;

}
