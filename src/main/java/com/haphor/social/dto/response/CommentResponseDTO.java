package com.haphor.social.dto.response;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import com.haphor.social.dto.CommentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CommentDTO comment;
	private boolean isSuccess;
	private String message;
	private HttpStatus httpStatus;

	
}
