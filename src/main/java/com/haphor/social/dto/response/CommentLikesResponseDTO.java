package com.haphor.social.dto.response;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;

import com.haphor.social.dto.LikeDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikesResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int commentId;
	private int userId;
	private Set<LikeDTO> likes = new HashSet<>();
	private boolean isSuccess;
	private String message;
	private HttpStatus httpStatus;

}
