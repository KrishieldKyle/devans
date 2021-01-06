package com.haphor.social.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haphor.social.model.Reply;

public interface ReplyDAO extends JpaRepository<Reply, Integer>{

	public Set<Reply> findByCommentCommentId(int commentId);
}
