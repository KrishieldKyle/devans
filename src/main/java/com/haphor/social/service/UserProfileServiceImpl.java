package com.haphor.social.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.haphor.social.dao.UserProfileDAO;
import com.haphor.social.dto.UserProfileDTO;
import com.haphor.social.dto.request.AddOrUpdateUserProfileRequestDTO;
import com.haphor.social.dto.response.AddOrUpdateUserProfileResponseDTO;
import com.haphor.social.model.User;
import com.haphor.social.model.UserProfile;
import com.haphor.social.util.converter.entity.ConvertEntity;
import com.haphor.social.util.jwt.AccessToken;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	private UserProfileDAO userProfileDao;
	
	@Autowired
	private AccessToken accessToken;
	
	@Autowired
	private ConvertEntity convertEntity;
	
	@Override
	public AddOrUpdateUserProfileResponseDTO saveOrUpdateUserProfile(AddOrUpdateUserProfileRequestDTO addOrUpdateUserProfileRequestDTO) {
		
		User user = accessToken.getUserFromToken();
		
		if(user.getUserId() != addOrUpdateUserProfileRequestDTO.getUserId()) {
			return new AddOrUpdateUserProfileResponseDTO(false, "User ID does not match to the currently logged in user", HttpStatus.UNAUTHORIZED, new UserProfileDTO());
		}
		
		// Check if email is already in use.
		boolean isEmailAlreadyUsed = userProfileDao.findByEmail(addOrUpdateUserProfileRequestDTO.getEmail().trim()) != null;
		
		UserProfile userProfile = new UserProfile();
		
		// Set the User on the Profile
		userProfile.setUser(user);
		userProfile.setEmail(addOrUpdateUserProfileRequestDTO.getEmail());
		userProfile.setFirstName(addOrUpdateUserProfileRequestDTO.getFirstName());
		userProfile.setLastName(addOrUpdateUserProfileRequestDTO.getLastName());
		userProfile.setMiddleName(addOrUpdateUserProfileRequestDTO.getMiddleName());
		userProfile.setImage(addOrUpdateUserProfileRequestDTO.getImage());

		// Check if user does not have a profile 
		if(user.getUserProfile() == null) {
			
			// Check if the email id is already used.
			if(isEmailAlreadyUsed) {
				
				return new AddOrUpdateUserProfileResponseDTO(false, "Email address already taken.", HttpStatus.UNPROCESSABLE_ENTITY, new UserProfileDTO());
			}
			// Save the profile
			UserProfile newUserProfile = userProfileDao.save(userProfile);
			
			UserProfileDTO userProfileDto = convertEntity.toUserProfileDTO(newUserProfile);

			return new AddOrUpdateUserProfileResponseDTO(true, "Profile has been saved.", HttpStatus.OK, userProfileDto);
		}
		
		// User has a profile already. Do update

		if(!user.getUserProfile().getEmail().trim().equalsIgnoreCase(userProfile.getEmail().trim()) && isEmailAlreadyUsed) {
			return new AddOrUpdateUserProfileResponseDTO(false, "Email address already taken.", HttpStatus.UNPROCESSABLE_ENTITY, new UserProfileDTO());
		}
		
		userProfile.setUserProfileId(user.getUserProfile().getUserProfileId());
		
		UserProfile newUserProfile = userProfileDao.save(userProfile);
		
		UserProfileDTO userProfileDto = convertEntity.toUserProfileDTO(newUserProfile);
		
		return new AddOrUpdateUserProfileResponseDTO(true, "Profile has been saved.", HttpStatus.OK, userProfileDto);
	}
}
