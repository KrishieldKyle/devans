package com.haphor.social.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.haphor.social.util.constants.NotificationAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int notificationId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "forUserId")
	private User forUser;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "byUserId")
	private User byUser;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "postId")
	private Post post;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "commentId")
	private Comment comment;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "replyId")
	private Reply reply;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "likeId")
	private Like like;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "action")
	private Action action = new Action();
	
	public void setAction(NotificationAction notificationAction) {
		action.setAction(notificationAction);
	}

}
