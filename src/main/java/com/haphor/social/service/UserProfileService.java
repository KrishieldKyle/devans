package com.haphor.social.service;

import com.haphor.social.dto.request.AddOrUpdateUserProfileRequestDTO;
import com.haphor.social.dto.response.AddOrUpdateUserProfileResponseDTO;

public interface UserProfileService {
	
	public AddOrUpdateUserProfileResponseDTO saveOrUpdateUserProfile(AddOrUpdateUserProfileRequestDTO addOrUpdateUserProfileRequestDTO);
	
}
