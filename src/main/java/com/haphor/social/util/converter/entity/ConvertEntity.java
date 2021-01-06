package com.haphor.social.util.converter.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.haphor.social.dto.LikeDTO;
import com.haphor.social.dto.TechnologyDTO;
import com.haphor.social.dto.TitleDTO;
import com.haphor.social.dto.UserDTO;
import com.haphor.social.dto.UserProfileDTO;
import com.haphor.social.model.Like;
import com.haphor.social.model.Technology;
import com.haphor.social.model.Title;
import com.haphor.social.model.User;
import com.haphor.social.model.UserProfile;

@Component
public class ConvertEntity {
	
	/**
	 * This method will convert the given set of Like Entities into a set of Like DTOs
	 * @param likes
	 * @return
	 */
	public Set<LikeDTO> toLikeDTOs(Set<Like> likes){
		
		Set<LikeDTO> likesDto = new HashSet<>();
		
		for(Like like : likes) {
			likesDto.add(new LikeDTO(like.getLikeId(), like.getUser().getUserId()));
		}
		
		return likesDto;
		
	}
	
	/**
	 * This method will convert the given set of Technology Entities into a set of Technology DTOs
	 * @param technologies
	 * @return
	 */
	public Set<TechnologyDTO> toTechnologyDTOs(Set<Technology> technologies){
		
		Set<TechnologyDTO> technologyDtos = new HashSet<>();
		
		for(Technology technology : technologies) {
			technologyDtos.add(new TechnologyDTO(technology.getTechnologyId(), technology.getName()));
		}
		
		return technologyDtos;
		
	}
	
	/**
	 * This method will convert the given Technology Entity into a Technology DTO
	 * @param technology
	 * @return
	 */
	public TechnologyDTO toTechnologyDTO(Technology technology){
		
		return new TechnologyDTO(technology.getTechnologyId(), technology.getName());
		
	}
	
	/**
	 * This method will convert the given set of Title Entities into a set of Title DTOs
	 * @param titles
	 * @return
	 */
	public Set<TitleDTO> toTitleDTOs(Set<Title> titles){
		
		Set<TitleDTO> titleDtos = new HashSet<>();
		
		for(Title title : titles) {
			titleDtos.add(new TitleDTO(title.getTitleId(), title.getName()));
		}
		
		return titleDtos;
		
	}
	
	/**
	 * This method will convert the given Title Entity into a Title DTO
	 * @param title
	 * @return
	 */
	public TitleDTO toTitleDTO(Title title){
		
		return new TitleDTO(title.getTitleId(), title.getName());
		
	}
	
	/**
	 * This method will convert the given User Profile Entity into a User Profile DTO
	 * @param userProfile
	 * @return
	 */
	public UserProfileDTO toUserProfileDTO(UserProfile userProfile) {
		
		return new UserProfileDTO(userProfile.getUser().getUserId(), userProfile.getUserProfileId(),
						userProfile.getEmail(), userProfile.getImage(), userProfile.getFirstName(), userProfile.getLastName(),
						userProfile.getMiddleName(), userProfile.getCreatedAt(), userProfile.getUpdatedAt());
		
	}
	
	/**
	 * This method will convert the given User Entity into a User DTO
	 * @param user
	 * @param titleDtos
	 * @param technologyDtos
	 * @param userProfileDTO
	 * @return
	 */
	public UserDTO toUserDTO(User user, Set<TitleDTO> titleDtos, Set<TechnologyDTO> technologyDtos, UserProfileDTO userProfileDTO) {
		return new UserDTO(user.getUserId(), user.getUsername(), user.getCreatedAt(), 
				user.getUpdatedAt(), titleDtos, technologyDtos, userProfileDTO);
		
	}

}
