package com.haphor.social.dto.response;

import java.io.Serializable;
import java.util.Set;

import org.springframework.http.HttpStatus;

import com.haphor.social.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllUsersResponseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isSuccess;
	private String message;
	private HttpStatus httpStatus;
	private Set<UserDTO> users;
}
