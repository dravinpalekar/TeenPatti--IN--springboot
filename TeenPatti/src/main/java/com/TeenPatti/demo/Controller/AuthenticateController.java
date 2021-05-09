package com.TeenPatti.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TeenPatti.demo.Cofiguration.Util.JwtUtil;
import com.TeenPatti.demo.RequestModel.AuthenticationRequestModel;
import com.TeenPatti.demo.RequestModel.TakeTokenRequestModel;
import com.TeenPatti.demo.Service.CustomUserDetailsService;

@RestController
public class AuthenticateController {
	
	@Autowired
	private JwtUtil jwtUtil; 
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@PostMapping("/authenticate")
	public  ResponseEntity<?>  AuthenticationAnyRequest(@RequestBody AuthenticationRequestModel authenticationRequest) throws Exception
	{
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		}catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Something is wrong!",e);
		}
		
		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		
		final String token = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new TakeTokenRequestModel(token));
	}
	
	
	
	
	@GetMapping("/")
	public String welcome()
	{
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//	    String token = request.getHeader("Authorization").split(" ")[1];
//		boolean a=jwtUtil.isTokenExpired(token);
		return "Welcome Dravin / Admin";
	}
	
	
	@RequestMapping("/hellouser")
	public String getUser()
	{
		return "Hello User";
	}
	
	@RequestMapping("/helloadmin")
	public String getAdmin()
	{
		return "Hello Admin";
	}

}
