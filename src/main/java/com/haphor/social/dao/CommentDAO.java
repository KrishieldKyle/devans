package com.haphor.social.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haphor.social.model.Comment;

@Repository
public interface CommentDAO extends JpaRepository<Comment, Integer>{
	
	public Set<Comment> findByPostPostId(int postId);

}
