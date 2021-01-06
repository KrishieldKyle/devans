package com.haphor.social.service;

import com.haphor.social.dto.request.AddOrUpdateReplyRequestDTO;
import com.haphor.social.dto.response.AllRepliesResponseDTO;
import com.haphor.social.dto.response.DeleteReplyResponseDTO;
import com.haphor.social.dto.response.ReplyResponseDTO;

public interface ReplyService {
	
	public AllRepliesResponseDTO getReplyByCommentId(int commentId);

	public ReplyResponseDTO addOrUpdateReply(AddOrUpdateReplyRequestDTO addOrUpdateReplyRequestDTO);
	
	public DeleteReplyResponseDTO deleteReply(int replyId);
	
}
