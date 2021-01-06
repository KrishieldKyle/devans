package com.haphor.social.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Technology {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int technologyId;
	@Column(nullable = false, unique= true)
	private String name;
	
	@JsonIgnore
	@ManyToMany(mappedBy="technologies", fetch=FetchType.LAZY)
	private Set<User> users;
	
	@JsonIgnore
	@ManyToMany(mappedBy="technologies", fetch=FetchType.LAZY)
	private Set<Post> posts;
	
	public Technology(int technologyId, String name) {
		this.technologyId = technologyId;
		this.name = name;
	}

}
