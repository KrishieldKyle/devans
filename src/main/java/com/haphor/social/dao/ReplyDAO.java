package com.haphor.social.dao;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haphor.social.model.Reply;

@Repository
public interface ReplyDAO extends JpaRepository<Reply, Integer>{

	public Set<Reply> findByCommentCommentId(int commentId);
}
