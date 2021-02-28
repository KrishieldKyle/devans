package com.haphor.social.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.haphor.social.model.util.AbstractTimestampEntity;

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
public class UserProfile extends AbstractTimestampEntity{

	
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
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId", nullable = false)
	private User user;


}
