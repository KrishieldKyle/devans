package com.haphor.social.util.jwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.haphor.social.dao.UserDAO;
import com.haphor.social.model.User;

@Component
public class AccessToken {
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private HttpServletRequest request;

	/**
	 * This method will get the Authorization Token from the request header then extract the current user.
	 * @return User
	 */
	public User getUserFromToken() {
		final String authorizationHeader = request.getHeader("Authorization");
		
		String jwt = authorizationHeader.substring(7);
		
		String username = jwtTokenUtil.extractUsername(jwt);
		
		return userDao.findByUsername(username);
	}

}
