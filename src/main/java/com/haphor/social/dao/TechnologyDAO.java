package com.haphor.social.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haphor.social.model.Technology;

@Repository
public interface TechnologyDAO extends JpaRepository<Technology, Integer> {
	
	
}