package com.prakash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prakash.configuration.JwtTokenProvider;
import com.prakash.exception.UserException;
import com.prakash.model.User;
import com.prakash.repositpry.UserRepository;
import com.prakash.request.LoginRequest;
import com.prakash.response.AuthResponse;
import com.prakash.service.implementation.CustomUserDetailsServiceImplementation;
import com.prakash.user.domain.UserRole;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private CustomUserDetailsServiceImplementation customUserDetailsServiceImplementation; 
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user)throws UserException{
		
		String email=user.getEmail();
		String password=user.getPassword();
		String firstName=user.getFirstName();
		String lastName=user.getLastName();
		String mobile=user.getMobile();
		UserRole role = user.getRole();
		
		User isEmailExist=userRepository.findByEmail(email);
		
		if(isEmailExist!=null)
			throw new UserException("Email Is Already Used With Another Account...");
		
		User newUser=new User();
		newUser.setEmail(email);
		newUser.setPassword(passwordEncoder.encode(password));
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setMobile(mobile);
		newUser.setRole(role);
		
		User savedUser = userRepository.save(newUser);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=jwtTokenProvider.generateToken(authentication);
		
		AuthResponse authResponse= new AuthResponse(token,"Sign up Successfully...");
		
        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
				
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest){
		String userName=loginRequest.getEmail();
		String password=loginRequest.getPassword();
		
		Authentication authentication=authenticate(userName,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token=jwtTokenProvider.generateToken(authentication);
		
		AuthResponse authResponse= new AuthResponse(token,"Sign in Successfully...");
		
        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
	}


	private Authentication authenticate(String userName, String password) {
		UserDetails userDetails=customUserDetailsServiceImplementation.loadUserByUsername(userName);
		
		if(userDetails==null)
			throw new BadCredentialsException("Invalid Username or Password");
		
		if(!passwordEncoder.matches(password, userDetails.getPassword()))
			throw new BadCredentialsException("Invalid Username or Password");
		return new UsernamePasswordAuthenticationToken ( userDetails, null, userDetails.getAuthorities());
	}
}
