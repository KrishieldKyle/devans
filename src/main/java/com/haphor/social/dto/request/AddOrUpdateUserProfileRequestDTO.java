package com.haphor.social.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddOrUpdateUserProfileRequestDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private String email;
	private String firstName;
	private String lastName;
	private String middleName;
	private String image;
	
}
