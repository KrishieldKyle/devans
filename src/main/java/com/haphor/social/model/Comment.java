package com.haphor.social.model;

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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.haphor.social.model.util.AbstractTimestampEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment extends AbstractTimestampEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commentId;
	@Column(nullable = false)
	private String content;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "postId", nullable = false)
	private Post post;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	@OneToMany(mappedBy="comment", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private Set<Reply> replies;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy="comment", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<Like> likes;
	
	@OneToMany(mappedBy="comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Notification> notifications;
	
}
