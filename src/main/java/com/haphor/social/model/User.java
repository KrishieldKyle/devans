package com.haphor.social.model;

import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	@Column(nullable = false, unique= true)
	private Date createdAt;
	private Date updatedAt;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Post> posts;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Comment> comments;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Like> likes;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinTable(name = "user_title", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "titleId"))
	private Set<Title> titles = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinTable(name = "user_technology", joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "technologyId"))
	private Set<Technology> technologies;

	@OneToOne(mappedBy="user", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	private UserProfile userProfile;
	
	@OneToMany(mappedBy="forUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Notification> notifications;
	
	@OneToMany(mappedBy="byUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Notification> notified;
	
	public void addTitle(Title title) {
		titles.add(title);
	}
	
	public void removeTitle(Title title) {
		boolean res = titles.remove(title);
		
		System.out.println(res);
		
	}

	@PrePersist
	protected void onCreate() {
		createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = new Date();
	}

}
