package com.prakash.service;

import com.prakash.exception.UserException;
import com.prakash.model.User;

public interface UserService {

	public User findUserById(Long userId)throws UserException;	
	public User findUserProfileByJwt(String jwt)throws UserException;	
}
