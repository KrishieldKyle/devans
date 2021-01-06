package com.haphor.social.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haphor.social.model.Notification;

@Repository
public interface NotificationDAO extends JpaRepository<Notification, Integer> {
	
	public Set<Notification> findByForUserUserId(int userId);
}
