package com.haphor.social.service;

import com.haphor.social.dto.request.AddOrUpdatePostRequestDTO;
import com.haphor.social.dto.request.AddTechnologiesToPostRequestDTO;
import com.haphor.social.dto.request.DeleteTechnologiesFromPostRequestDTO;
import com.haphor.social.dto.response.AllPostsResponseDTO;
import com.haphor.social.dto.response.DeletePostResponseDTO;
import com.haphor.social.dto.response.PostResponseDTO;
import com.haphor.social.dto.response.PostTechnologyResponseDTO;

public interface PostService {
	
	public AllPostsResponseDTO getAllPost();
	
	public PostResponseDTO getPost(int postId);
	
	public PostResponseDTO saveOrUpdatePost(AddOrUpdatePostRequestDTO addOrUpdatePostRequestDTO);
	
	public DeletePostResponseDTO deletePost(int postId);

	public PostTechnologyResponseDTO addTechnologiesToPost(AddTechnologiesToPostRequestDTO addTechnologiesToPostRequestDTO);

	public PostTechnologyResponseDTO deleteTechnologiesFromPost(DeleteTechnologiesFromPostRequestDTO deleteTechnologiesFromPostRequestDTO);

}
