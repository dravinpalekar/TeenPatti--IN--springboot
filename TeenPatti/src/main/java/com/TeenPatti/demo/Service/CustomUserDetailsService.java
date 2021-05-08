package com.TeenPatti.demo.Service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.TeenPatti.demo.Entity.UserEntity;
import com.TeenPatti.demo.Repository.UserEntityRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserEntityRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<SimpleGrantedAuthority> roles = null;
		
		UserEntity  userEntity=userRepository.findByUsername(username);
//		return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
		
		if (userEntity != null) {
			roles = Arrays.asList(new SimpleGrantedAuthority(userEntity.getRole()));
			return new User(userEntity.getUsername(), userEntity.getPassword(), roles);
		}
		throw new UsernameNotFoundException("User not found with the name " + username);
	}

}
