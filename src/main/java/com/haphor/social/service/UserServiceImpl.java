package com.haphor.social.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.haphor.social.dao.TechnologyDAO;
import com.haphor.social.dao.TitleDAO;
import com.haphor.social.dao.UserDAO;
import com.haphor.social.dto.TechnologyDTO;
import com.haphor.social.dto.TitleDTO;
import com.haphor.social.dto.UserDTO;
import com.haphor.social.dto.UserProfileDTO;
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
import com.haphor.social.model.Technology;
import com.haphor.social.model.Title;
import com.haphor.social.model.User;
import com.haphor.social.model.UserProfile;
import com.haphor.social.util.converter.dto.ConvertDTO;
import com.haphor.social.util.converter.entity.ConvertEntity;
import com.haphor.social.util.jwt.AccessToken;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private TitleDAO titleDao;
	
	@Autowired
	private TechnologyDAO technologyDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AccessToken accessToken;
	
	@Autowired
	private ConvertEntity convertEntity;
	
	@Autowired
	private ConvertDTO convertDTO;
	
	public UserRegistrationResponseDTO saveUser(UserRegistrationRequestDTO userRegistrationRequestDto) {
		
		
		if(userDao.findByUsername(userRegistrationRequestDto.getUsername()) == null) {
			
			User user = new User();
			
			user.setUsername(userRegistrationRequestDto.getUsername());
			user.setPassword(passwordEncoder.encode(userRegistrationRequestDto.getPassword()));
			
			userDao.save(user);
			
			return new UserRegistrationResponseDTO(true, userRegistrationRequestDto.getUsername(), 
					"You have successfully registered!", HttpStatus.OK);
		}

		return new UserRegistrationResponseDTO(false, userRegistrationRequestDto.getUsername(), 
				"Username is already taken.", HttpStatus.UNAUTHORIZED);
	}
	
	public UserResponseDTO getUserById(int userId) {
		
		Optional<User> maybeUser = userDao.findById(userId);
		
		if(maybeUser.isPresent()) {
			
			User user = maybeUser.get();
			
			UserProfile userProfile = user.getUserProfile();
			
			UserProfileDTO userProfileDTO = null;
			
			if(userProfile != null) {
				userProfileDTO = convertEntity.toUserProfileDTO(userProfile);
			}
			
			Set<TitleDTO> titleDtos = convertEntity.toTitleDTOs(user.getTitles());
			
			Set<TechnologyDTO> technologyDtos = convertEntity.toTechnologyDTOs(user.getTechnologies());
			
			UserDTO userDto = convertEntity.toUserDTO(user, titleDtos, technologyDtos, userProfileDTO);
			
			return new UserResponseDTO(true, "Successfully fetched the user", HttpStatus.OK, userDto);
		}
		
		
		return new UserResponseDTO(false, "User not found", HttpStatus.NOT_FOUND, null);
	}
	
	public AllUsersResponseDTO getAllUsers(){
		
		List<User> users = userDao.findAll();
		
		if(users.isEmpty()) {
			return new AllUsersResponseDTO(true, "No users that are currently registered", HttpStatus.OK, new HashSet<>());
		}
		
		Set<UserDTO> userDtos = new HashSet<>();
		
		UserDTO userDto = null;
		
		UserProfile userProfile = null;
		
		UserProfileDTO userProfileDTO = null;
		
		for(User user : users) {
			
			userProfile = user.getUserProfile();
			
			if(userProfile != null) {
				userProfileDTO = convertEntity.toUserProfileDTO(userProfile);
				
			}
			
			Set<TitleDTO> titleDtos = convertEntity.toTitleDTOs(user.getTitles());
			
			Set<TechnologyDTO> technologyDtos = convertEntity.toTechnologyDTOs(user.getTechnologies());
			
			userDto = convertEntity.toUserDTO(user, titleDtos, technologyDtos, userProfileDTO);
			
			userDtos.add(userDto);
		}
		
		return new AllUsersResponseDTO(true, "Users successfully fetched", HttpStatus.OK, userDtos);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userDao.findByUsername(username);
		System.out.println("loadUserByUsername: Line 50");
		
		if(user == null) {
			throw new UsernameNotFoundException("Username and password do not match");
		}
		System.out.println("loadUserByUsername: Line 55");
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

	@Override
	public User getUserByUsername(String username) {
		User user = userDao.findByUsername(username);
		return user;
	}

	@Override
	public UserUpdatePasswordResponseDTO updatePassword(UserPasswordUpdateRequestDTO userPasswordUpdateRequestDTO) {
		
		User user = accessToken.getUserFromToken();
		
		user.setPassword(passwordEncoder.encode(userPasswordUpdateRequestDTO.getNewPassword()));
		
		user = userDao.save(user);
		
		if(user == null) {
			return new UserUpdatePasswordResponseDTO(false, "Something went wrong, please try again later", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new UserUpdatePasswordResponseDTO(true, "Password successfully updated", HttpStatus.OK);
	}

	@Override
	public UserTitlesResponseDTO addTitlesToUser(AddTitlesToUserRequestDTO addTitlesToUserRequestDTO) {
		
		User user = accessToken.getUserFromToken();

		if(user.getUserId() != addTitlesToUserRequestDTO.getUserId()) {
			return new UserTitlesResponseDTO(false, "User ID does not match to the currently logged in user",addTitlesToUserRequestDTO.getUserId(), new HashSet<>(), HttpStatus.UNAUTHORIZED);
		}
		
		if(addTitlesToUserRequestDTO.getTitles().size() == 0 ) {
			return new UserTitlesResponseDTO(true, "No title has been added.", addTitlesToUserRequestDTO.getUserId(), new HashSet<>(), HttpStatus.OK);
		}
		
		Set<Title> newTitles = convertDTO.toTitleEntities(addTitlesToUserRequestDTO.getTitles());
		
		titleDao.saveAll(newTitles);
		
		Set<Title> userTitles = user.getTitles();
		
		userTitles.addAll(newTitles);
		
		// Set the new titles to user
		user.setTitles(userTitles);
		
		// Persists the user
		user = userDao.save(user);
		
		// Construct the response object
		
		Set<TitleDTO> newTitleDtos = convertEntity.toTitleDTOs(user.getTitles());
		
		return new UserTitlesResponseDTO(true, "Titles have successfully added to the user", addTitlesToUserRequestDTO.getUserId(), newTitleDtos, HttpStatus.OK);
		
	}

	@Override
	public UserTitlesResponseDTO deleteTitlesFromUser(DeleteTitlesFromUserRequestDTO deleteTitlesFromUserRequestDTO) {
		
		User user = accessToken.getUserFromToken();
		
		if(user.getUserId() != deleteTitlesFromUserRequestDTO.getUserId()) {
			return new UserTitlesResponseDTO(false, "User ID does not match to the currently logged in user",deleteTitlesFromUserRequestDTO.getUserId(), new HashSet<>(), HttpStatus.UNAUTHORIZED);
		}
		
		if(deleteTitlesFromUserRequestDTO.getTitles().size() == 0 ) {
			return new UserTitlesResponseDTO(true, "No title has been deleted.", deleteTitlesFromUserRequestDTO.getUserId(), new HashSet<>(), HttpStatus.OK);
		}
		
		Set<Title> currentTitles = user.getTitles();
		
		for(TitleDTO titleDto : deleteTitlesFromUserRequestDTO.getTitles()) {
			
			for(Title userTitle : user.getTitles()) {
				if(userTitle.getTitleId() == titleDto.getTitleId()) {
					currentTitles.remove(userTitle);
					break;
				}
			}
		}
		
		user.setTitles(currentTitles);
		
		userDao.save(user);
		
		Set<TitleDTO> titleDtos = convertEntity.toTitleDTOs(user.getTitles());
		
		return new UserTitlesResponseDTO(true, "Title(s) successfully deleted.", deleteTitlesFromUserRequestDTO.getUserId(), titleDtos, HttpStatus.OK);
	}
	
	@Override
	public UserTechnologiesResponseDTO addTechnologiesToUser(AddTechnologiesToUserRequestDTO addTechnologiesToUserRequestDTO) {
		
		User user = accessToken.getUserFromToken();

		if(user.getUserId() != addTechnologiesToUserRequestDTO.getUserId()) {
			return new UserTechnologiesResponseDTO(false, "User ID does not match to the currently logged in user",addTechnologiesToUserRequestDTO.getUserId(), new HashSet<>(), HttpStatus.UNAUTHORIZED);
		}
		
		if(addTechnologiesToUserRequestDTO.getTechnologies().isEmpty()) {
			return new UserTechnologiesResponseDTO(true, "No technology has been added.", addTechnologiesToUserRequestDTO.getUserId(), new HashSet<>(), HttpStatus.OK);
		}
		
		Set<Technology> newTechnologies = convertDTO.toTechnologyEntities(addTechnologiesToUserRequestDTO.getTechnologies());
		
		technologyDao.saveAll(newTechnologies);
		
		Set<Technology> userTechnologies = user.getTechnologies();
		
		userTechnologies.addAll(newTechnologies);
		
		// Set the new technologies to user
		user.setTechnologies(userTechnologies);
		
		// Persists the user
		user = userDao.save(user);
		
		// Construct the response object
		
		Set<TechnologyDTO> newTechnologyDtos = convertEntity.toTechnologyDTOs(user.getTechnologies());
		
		return new UserTechnologiesResponseDTO(true, "Technologies have successfully added to the user", addTechnologiesToUserRequestDTO.getUserId(), newTechnologyDtos, HttpStatus.OK);
	}

	@Override
	public UserTechnologiesResponseDTO deleteTechnologiesFromUser(DeleteTechnologiesFromUserRequestDTO deleteTechnologiesFromUserRequestDTO) {
		
		User user = accessToken.getUserFromToken();
		
		if(user.getUserId() != deleteTechnologiesFromUserRequestDTO.getUserId()) {
			return new UserTechnologiesResponseDTO(false, "User ID does not match to the currently logged in user",deleteTechnologiesFromUserRequestDTO.getUserId(), new HashSet<>(), HttpStatus.UNAUTHORIZED);
		}
		
		if(deleteTechnologiesFromUserRequestDTO.getTechnologies().isEmpty()) {
			return new UserTechnologiesResponseDTO(true, "No technology has been deleted.", deleteTechnologiesFromUserRequestDTO.getUserId(), new HashSet<>(), HttpStatus.OK);
		}
		
		Set<Technology> currentTechnologies = user.getTechnologies();
		
		for(TechnologyDTO technologyDto : deleteTechnologiesFromUserRequestDTO.getTechnologies()) {
			
			for(Technology userTechnology : user.getTechnologies()) {
				if(userTechnology.getTechnologyId() == technologyDto.getTechnologyId()) {
					currentTechnologies.remove(userTechnology);
					break;
				}
			}
		}
		
		user.setTechnologies(currentTechnologies);
		
		userDao.save(user);
		
		Set<TechnologyDTO> technologyDtos = convertEntity.toTechnologyDTOs(user.getTechnologies());
		
		return new UserTechnologiesResponseDTO(true, "Technologies successfully deleted.", deleteTechnologiesFromUserRequestDTO.getUserId(), technologyDtos, HttpStatus.OK);
		
	}


}
