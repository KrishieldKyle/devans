package com.haphor.social.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
import com.haphor.social.model.User;
import com.haphor.social.util.jwt.AccessToken;

@Service
public class InputValidatorServiceImpl implements InputValidatorService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AccessToken accessToken;
	
	private String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
             "[a-zA-Z0-9_+&*-]+)*@" + 
             "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
             "A-Z]{2,7}$"; 
	
	private boolean isValid(String email) { 
                
        Pattern pat = Pattern.compile(emailRegex); 
        return pat.matcher(email).matches(); 
    } 
	
	public Map<String, Object> validateUserRegisterInput(UserRegistrationRequestDTO userRegistrationDTO){
		
		Map<String, Object> message = new HashMap<>();
		
		String username = Objects.toString(userRegistrationDTO.getUsername(),"");
		String password = Objects.toString(userRegistrationDTO.getPassword(),"");
		String confirmPassword = Objects.toString(userRegistrationDTO.getConfirmPassword(),"");
		
		if(username.contains(" ")) {
			message.put("username", "Please do not include whitespace to your username.");
		}
		else if(username.isEmpty() || username.equals(null)) {
			message.put("username", "Username is required.");
		}
		else if(username.length() < 3 || username.length() > 15) {
			message.put("username", "Username must be at least 3 to 15 characters only.");
		}
		
		if(password.contains(" ")) {
			message.put("password", "Please do not include whitespace to your password.");
		}
		else if(password.isEmpty() || password.equals(null)) {
			message.put("password", "Password is required.");
		}
		else if(password.length() < 6 || password.length() > 30) {
			message.put("password", "Password must be at least 6 to 30 characters only.");
		}
		
		if(confirmPassword.isEmpty() || confirmPassword.equals(null)) {
			message.put("confirmPassword", "Please re-type your password.");
		}
		else if(!confirmPassword.equals(password)) {
			message.put("confirmPassword", "Confirm password does not match.");
		}
		
		return message;
		
	}
	
	public Map<String, Object> validateUserLoginInput(AuthenticationRequestDTO user){
		
		Map<String, Object> message = new HashMap<>();
		
		String username = Objects.toString(user.getUsername(),"");
		String password = Objects.toString(user.getPassword(),"");
		
		if(username.isEmpty() || username.equals(null)) {
			message.put("username", "Please enter your username.");
		}
	
		if(password.isEmpty() || password.equals(null)) {
			message.put("password", "Please enter your password.");
		}
		
		return message;
		
	}

	@Override
	public Map<String, Object> validateUserProfileInput(AddOrUpdateUserProfileRequestDTO addOrUpdateUserProfileRequestDTO) {
		
		Map<String, Object> message = new HashMap<>();
		
		String email = Objects.toString(addOrUpdateUserProfileRequestDTO.getEmail(), "");
		String firstName = Objects.toString(addOrUpdateUserProfileRequestDTO.getFirstName(), "");
		String lastName = Objects.toString(addOrUpdateUserProfileRequestDTO.getLastName(), "");
		
		if(email.isEmpty() || email.equals(null)) {
			message.put("email", "Email is required.");
		}
		else if(!isValid(email)) {
			message.put("email", "Email is invalid.");
		}
		
		if(firstName.isEmpty() || firstName.equals(null)) {
			message.put("firstName", "First name is required.");
		}
		
		if(lastName.isEmpty() || lastName.equals(null)) {
			message.put("lastName", "Last name is required.");
		}
		return message;
	}

	@Override
	public Map<String, Object> validateUserUpdatePasswordInput(UserPasswordUpdateRequestDTO userPasswordUpdateRequestDTO) {
		
		Map<String, Object> message = new HashMap<>();
		
		int userId = userPasswordUpdateRequestDTO.getUseriId();
		String oldPassword = Objects.toString(userPasswordUpdateRequestDTO.getOldPassword(), "");
		String newPassword = Objects.toString(userPasswordUpdateRequestDTO.getNewPassword(), "");
		String confirmPassword = Objects.toString(userPasswordUpdateRequestDTO.getConfirmPassword(), "");
		
		User user = accessToken.getUserFromToken();
		
		if(userId != user.getUserId()) {
			message.put("userId", "User ID given does not match to the currently logged in user.");
		}
		
		if(oldPassword.isEmpty() || oldPassword.equals(null)) {
			message.put("oldPassword", "Old Password is required.");
		}
		else if(!passwordEncoder.matches(oldPassword, user.getPassword())) {
			message.put("oldPassword", "Old Password is not valid.");
		}
		else if(oldPassword.equals(newPassword)) {
			message.put("newPassword", "New password cannot be the same as the old password.");
		}
		
		if(newPassword.contains(" ")) {
			message.put("newPassword", "Please do not include whitespace to your password.");
		}
		else if(newPassword.isEmpty() || newPassword.equals(null)) {
			message.put("newPassword", "New password is required.");
		}
		else if(newPassword.length() < 6 || newPassword.length() > 30) {
			message.put("newPassword", "Password must be at least 6 to 30 characters only.");
		}
		
		
		if(confirmPassword.isEmpty() || confirmPassword.equals(null)) {
			message.put("confirmPassword", "Please re-type your password.");
		}
		else if(!confirmPassword.equals(newPassword)) {
			message.put("confirmPassword", "Confirm password does not match.");
		}
		
		
		return message;
	}

	@Override
	public Map<String, Object> validateUserTitleInput(Title title) {
		Map<String, Object> message = new HashMap<>();
		
		String name = Objects.toString(title.getName(), "");
		
		if(name.isEmpty() || name.equals(null)) {
			message.put("name", "Title name is required.");
		}
		
		return message;
	}

	@Override
	public Map<String, Object> validateUserTechnologyInput(Technology technology) {
		Map<String, Object> message = new HashMap<>();
		
		String name = Objects.toString(technology.getName(), "");
		
		if(name.isEmpty() || name.equals(null)) {
			message.put("name", "Technology name is required.");
		}
		
		return message;
	}

	@Override
	public Map<String, Object> validatePostTechnologyInput(AddTechnologiesToPostRequestDTO postTechnologyDto) {
		
		Map<String, Object> message = new HashMap<>();
		
		String postId = Objects.toString(postTechnologyDto.getPostId(), "");
		
		if(postId.isEmpty() || postId.equals(null)) {
			message.put("postId", "Post cannot be found.");
		}
		
		return message;
	}

	@Override
	public Map<String, Object> validatePostInput(AddOrUpdatePostRequestDTO addOrUpdatePostRequestDTO) {
		Map<String, Object> message = new HashMap<>();
		
		String subject = Objects.toString(addOrUpdatePostRequestDTO.getSubject(), "");
		String content = Objects.toString(addOrUpdatePostRequestDTO.getContent(), "");
		
		if(subject.isEmpty() || subject.equals(null)) {
			message.put("subject", "Subject is required.");
		}
		
		if(content.isEmpty() || content.equals(null)) {
			message.put("content", "Content is required.");
		}
		
		return message;
	}

	@Override
	public Map<String, Object> validateCommentInput(AddOrUpdateCommentRequestDTO addOrUpdateCommentRequestDTO) {
		
		Map<String, Object> message = new HashMap<>();
		
		String postId = Objects.toString(addOrUpdateCommentRequestDTO.getPostId(), "");
		String content = Objects.toString(addOrUpdateCommentRequestDTO.getContent(), "");
		
		if(postId.isEmpty() || postId.equals(null)) {
			message.put("postId", "Post cannot be found.");
		}
		
		if(content.isEmpty() || content.equals(null)) {
			message.put("content", "Content is required.");
		}
		
		return message;
	}

	@Override
	public Map<String, Object> validateReplyInput(AddOrUpdateReplyRequestDTO addOrUpdateReplyRequestDTO) {
		
		Map<String, Object> message = new HashMap<>();
		
		String commentId = Objects.toString(addOrUpdateReplyRequestDTO.getCommentId(), "");
		String content = Objects.toString(addOrUpdateReplyRequestDTO.getContent(), "");
		
		if(commentId.isEmpty() || commentId.equals(null)) {
			message.put("commentId", "Comment cannot be found.");
		}
		
		if(content.isEmpty() || content.equals(null)) {
			message.put("content", "Content is required.");
		}
		
		return message;
	}

	@Override
	public Map<String, Object> validatePostTechnologyInput(
			DeleteTechnologiesFromPostRequestDTO deleteTechnologiesFromPostRequestDTO) {
		Map<String, Object> message = new HashMap<>();
		
		String postId = Objects.toString(deleteTechnologiesFromPostRequestDTO.getPostId(), "");
		
		if(postId.isEmpty() || postId.equals(null)) {
			message.put("postId", "Post cannot be found.");
		}
		
		return message;
	}

}
