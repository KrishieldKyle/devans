package com.haphor.social.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private int profileId;
	private String email;
	private String image;
	private String firstName;
	private String lastName;
	private String middleName;
	private Date createdAt;
	private Date updatedAt;

}
