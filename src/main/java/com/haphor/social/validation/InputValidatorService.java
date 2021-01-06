package com.haphor.social.validation;

import java.util.Map;

import com.haphor.social.dto.request.AddOrUpdateCommentRequestDTO;
import com.haphor.social.dto.request.AddOrUpdatePostRequestDTO;
import com.haphor.social.dto.request.AddOrUpdateReplyRequestDTO;
import com.haphor.social.dto.request.AddOrUpdateUserProfileRequestDTO;
import com.haphor.social.dto.request.AddTechnologiesToPostRequestDTO;
import com.haphor.social.dto.request.AuthenticationRequestDTO;
import com.haphor.social.dto.request.DeleteTechnologiesFromPostRequestDTO;
import com.haphor.social.dto.request.UserPasswordUpdateRequestDTO;
import com.haphor.social.dto.request.UserRegistrationRequestDTO;
import com.haphor.social.model.Technology;
import com.haphor.social.model.Title;

public interface InputValidatorService {
	
	public Map<String, Object> validateUserRegisterInput(UserRegistrationRequestDTO userRegistrationDTO);
	
	public Map<String, Object> validateUserLoginInput(AuthenticationRequestDTO authenticationRequest);
	
	public Map<String, Object> validateUserProfileInput(AddOrUpdateUserProfileRequestDTO userProfileDTO);
	
	public Map<String, Object> validateUserUpdatePasswordInput(UserPasswordUpdateRequestDTO userPasswordUpdateDTO);
	
	public Map<String, Object> validateUserTitleInput(Title title);
	
	public Map<String, Object> validateUserTechnologyInput(Technology technology);
	
	public Map<String, Object> validatePostTechnologyInput(AddTechnologiesToPostRequestDTO addTechnologiesToPostRequestDTO);
	
	public Map<String, Object> validatePostTechnologyInput(DeleteTechnologiesFromPostRequestDTO deleteTechnologiesFromPostRequestDTO);

	public Map<String, Object> validatePostInput(AddOrUpdatePostRequestDTO addOrUpdatePostRequestDTO);
	
	public Map<String, Object> validateCommentInput(AddOrUpdateCommentRequestDTO addOrUpdateCommentRequestDTO);
	
	public Map<String, Object> validateReplyInput(AddOrUpdateReplyRequestDTO addOrUpdateReplyRequestDTO);
	
}
