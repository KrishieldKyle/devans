package com.haphor.social.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="likes") 
public class Like {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int likeId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "postId")
	private Post post;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "commentId")
	private Comment comment;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "replyId")
	private Reply reply;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	@OneToOne(mappedBy="like", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Notification notification;

}
