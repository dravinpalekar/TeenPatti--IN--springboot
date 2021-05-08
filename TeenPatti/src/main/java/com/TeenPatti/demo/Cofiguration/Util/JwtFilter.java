package com.TeenPatti.demo.Cofiguration.Util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtFilter extends OncePerRequestFilter {
	

	@Autowired
    private JwtUtil jwtUtil;
	
//	@Autowired
//    private CustomUserDetailsService customUserDetailsService;
	
	@Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

		try{
			// JWT Token is in the form "Bearer token". Remove Bearer word and
			// get  only the Token
			String jwtToken = extractJwtFromRequest(httpServletRequest);

			if (StringUtils.hasText(jwtToken) && jwtUtil.validateToken(jwtToken)) {
				UserDetails userDetails = new User(jwtUtil.getUsernameFromToken(jwtToken), "",
						jwtUtil.getRolesFromToken(jwtToken));

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				System.out.println("Cannot set the Security Context");
			}
		 }catch(ExpiredJwtException ex)
		 {
			 httpServletRequest.setAttribute("exception", ex);
		 }
		 catch(BadCredentialsException ex)
		 {
			 httpServletRequest.setAttribute("exception", ex);
		 }
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}
	
	private String extractJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

}
