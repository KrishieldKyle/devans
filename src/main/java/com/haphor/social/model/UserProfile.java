package com.haphor.social.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userProfileId;
	@Column(nullable = false, unique= true)
	private String email;
	private String image;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	private String middleName;
	private Date createdAt;
	private Date updatedAt;
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	@PrePersist
	protected void onCreate() {
		System.out.println("onCreate");
		createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		System.out.println("onUpdate");
		updatedAt = new Date();
	}

}
