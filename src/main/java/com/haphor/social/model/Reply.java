package com.haphor.social.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reply {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int replyId;
	@Column(nullable = false)
	private String content;
	private Date createdAt;
	private Date updatedAt;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "commentId", nullable = false)
	private Comment comment;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy="reply", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<Like> likes;
	
	@OneToMany(mappedBy="reply", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Notification> notifications;
	
	@PrePersist
	protected void onCreate() {
		createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = new Date();
	}

}
