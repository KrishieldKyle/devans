package com.haphor.social.dto.request;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.haphor.social.dto.TechnologyDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddOrUpdatePostRequestDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int userId;
	private int postId;
	private String subject;
	private String content;
	private Set<TechnologyDTO> technologies = new HashSet<>();

}
