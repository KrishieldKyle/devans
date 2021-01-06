package com.haphor.social.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int postId;
	private int userId;
	private String subject;
	private String content;
	private Set<LikeDTO> likes;
	private Set<TechnologyDTO> technologies;
	private Date createdAt;
	private Date updatedAt;
	

}
