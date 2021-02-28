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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
public class Post extends AbstractTimestampEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	@Column(nullable = false)
	private String subject;
	@Column(nullable = false)
	private String content;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	@OneToMany(mappedBy="post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private Set<Comment> comments;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH})
	@JoinTable(name = "post_technology", joinColumns = @JoinColumn(name = "postId"), inverseJoinColumns = @JoinColumn(name = "technologyId"))
	private Set<Technology> technologies;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy="post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Set<Like> likes;
	
	@OneToMany(mappedBy="post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Notification> notifications;

}
