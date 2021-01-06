package com.haphor.social.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haphor.social.model.Post;

@Repository
public interface PostDAO extends JpaRepository<Post, Integer> {
	
	

}
