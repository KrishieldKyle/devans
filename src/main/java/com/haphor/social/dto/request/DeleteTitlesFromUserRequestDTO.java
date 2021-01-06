package com.haphor.social.dto.request;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.haphor.social.dto.TitleDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteTitlesFromUserRequestDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int userId;
	private Set<TitleDTO> titles = new HashSet<>();

}
