package com.haphor.social.dto.response;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;

import com.haphor.social.dto.TitleDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllTitlesResponseDTO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Set<TitleDTO> titles = new HashSet<>();
	private boolean isSuccess;
	private String message;
	private HttpStatus httpStatus;

}
