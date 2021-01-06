package com.haphor.social.util.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.haphor.social.service.UserService;
import com.haphor.social.util.jwt.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	

	@Autowired
	private UserService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authorizationHeader = request.getHeader("Authorization");
		
		String username= null;
		String jwt = null;
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			username = jwtTokenUtil.extractUsername(jwt);
		}
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if(jwtTokenUtil.validate(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);
		
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		
		boolean doNotFilter = false;
		
		if(request.getMethod().equals(HttpMethod.GET.toString()) && !request.getServletPath().startsWith("/api/notification/")) {
			doNotFilter = true;
		}
		else if(request.getMethod().equals(HttpMethod.POST.toString()) && (request.getServletPath().equals("/api/user/register") || request.getServletPath().equals("/api/user/login"))) {
			doNotFilter = true;
		}
		
		return doNotFilter;
	}
	
	

}
