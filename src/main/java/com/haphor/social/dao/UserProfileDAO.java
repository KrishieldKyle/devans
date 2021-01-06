package com.haphor.social.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haphor.social.model.UserProfile;

@Repository
public interface UserProfileDAO extends JpaRepository<UserProfile, Integer>{
	
	public UserProfile findByEmail(String email);

}
