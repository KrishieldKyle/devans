package com.haphor.social.dto;

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
public class CommentDTO {
	
	private int postId;
	private int userId;
	private int commentId;
	private String content;
	private Set<LikeDTO> likes;
	private Date createdAt;
	private Date updatedAt;

}
