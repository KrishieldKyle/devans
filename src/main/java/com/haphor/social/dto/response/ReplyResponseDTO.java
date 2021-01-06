package com.haphor.social.dto.response;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import com.haphor.social.dto.ReplyDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ReplyDTO reply;
	private boolean isSuccess;
	private String message;
	private HttpStatus httpStatus;

	
}
