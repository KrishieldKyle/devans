package com.haphor.social.dao;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.haphor.social.model.Like;

@Repository
public interface LikeDAO extends JpaRepository<Like, Integer> {
	
	public Set<Like> findByPostPostId(int postId);
	
	public Set<Like> findByCommentCommentId(int commentId);
	
	public Set<Like> findByReplyReplyId(int replyId);
	
	public Optional<Like> findByPostPostIdAndUserUserId(int postId, int userId);
	
	public Optional<Like> findByCommentCommentIdAndUserUserId(int commentId, int userId);
	
	public Optional<Like> findByReplyReplyIdAndUserUserId(int replyId, int userId);

}
