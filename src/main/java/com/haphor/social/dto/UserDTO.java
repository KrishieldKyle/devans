package com.haphor.social.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int userId;
	private String username;
	private Date createAt;
	private Date updatedAt;
	
	private Set<TitleDTO> titles = new HashSet<>();
	private Set<TechnologyDTO> technologies = new HashSet<>();
	
	private UserProfileDTO profile;

}
