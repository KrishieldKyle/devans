package com.haphor.social.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haphor.social.model.Title;

@Repository
public interface TitleDAO extends JpaRepository<Title, Integer> {
	
	
}