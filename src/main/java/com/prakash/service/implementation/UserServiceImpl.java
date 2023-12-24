package com.prakash.service.implementation;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.prakash.configuration.JwtTokenProvider;
import com.prakash.exception.UserException;
import com.prakash.model.User;
import com.prakash.repositpry.UserRepository;
import com.prakash.service.UserService;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
	
	private UserRepository userRepository;
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public User findUserById(Long userId) throws UserException {

		Optional<User> user = userRepository.findById(userId);
		
		if(user.isPresent()) {
			return user.get();
		}
		
		throw new UserException("User not foung with id :"+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		
		String email = jwtTokenProvider.getEmailFromJwtToken(jwt);
		User user = userRepository.findByEmail(email);
		if(user==null)
			throw new UserException("User not found with email : "+email);
		return user;
	}

}
