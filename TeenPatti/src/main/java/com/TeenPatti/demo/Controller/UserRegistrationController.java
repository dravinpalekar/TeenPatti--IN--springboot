package com.TeenPatti.demo.Controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.TeenPatti.demo.Entity.UserEntity;
import com.TeenPatti.demo.Service.UserEntityService;

@Controller
@RequestMapping("/register")
@RestController
public class UserRegistrationController {
	
	private static final Logger log = LoggerFactory.getLogger(UserRegistrationController.class);
	
	@Autowired
	UserEntityService userService;
	
	@PostMapping(value = "/user", headers = "Accept=application/json")
	@ResponseStatus(HttpStatus.CREATED)
	Map<String, String> grouptest(@RequestBody UserEntity userEntity ) throws Exception {
		
		log.error("Creating Username : " + userEntity.getUsername() + "\n" + "AND" + "\n" + "Email address : " + userEntity.getEmail());
		return userService.RegisterUser(userEntity);
	}

}
