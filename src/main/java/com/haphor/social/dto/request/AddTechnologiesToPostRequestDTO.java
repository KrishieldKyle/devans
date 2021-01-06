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
public class AddTechnologiesToPostRequestDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int postId;
	private int userId;
	private Set<TechnologyDTO> technologies = new HashSet<>();

}
