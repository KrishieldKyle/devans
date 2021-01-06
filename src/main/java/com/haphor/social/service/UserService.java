package com.haphor.social.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.haphor.social.dto.request.AddTechnologiesToUserRequestDTO;
import com.haphor.social.dto.request.AddTitlesToUserRequestDTO;
import com.haphor.social.dto.request.DeleteTechnologiesFromUserRequestDTO;
import com.haphor.social.dto.request.DeleteTitlesFromUserRequestDTO;
import com.haphor.social.dto.request.UserPasswordUpdateRequestDTO;
import com.haphor.social.dto.request.UserRegistrationRequestDTO;
import com.haphor.social.dto.response.AllUsersResponseDTO;
import com.haphor.social.dto.response.UserTechnologiesResponseDTO;
import com.haphor.social.dto.response.UserTitlesResponseDTO;
import com.haphor.social.dto.response.UserRegistrationResponseDTO;
import com.haphor.social.dto.response.UserResponseDTO;
import com.haphor.social.dto.response.UserUpdatePasswordResponseDTO;
import com.haphor.social.model.User;

public interface UserService extends UserDetailsService{
	
	public UserRegistrationResponseDTO saveUser(UserRegistrationRequestDTO userRegistrationRequestDto);
	
	public User getUserByUsername(String username);
	
	public UserResponseDTO getUserById(int userId);
	
	public AllUsersResponseDTO getAllUsers();
	
	public UserUpdatePasswordResponseDTO updatePassword(UserPasswordUpdateRequestDTO userPasswordUpdateDTO);

	public UserTitlesResponseDTO addTitlesToUser(AddTitlesToUserRequestDTO addTitlesToUserRequestDtos);

	public UserTitlesResponseDTO deleteTitlesFromUser(DeleteTitlesFromUserRequestDTO deleteTitlesFromUserRequestDTO);

	public UserTechnologiesResponseDTO addTechnologiesToUser(AddTechnologiesToUserRequestDTO addTechnologiesToUserRequestDtos);

	public UserTechnologiesResponseDTO deleteTechnologiesFromUser(DeleteTechnologiesFromUserRequestDTO deleteTechnologiesFromUserRequestDTO);

}
