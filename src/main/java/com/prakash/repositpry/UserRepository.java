package com.prakash.repositpry;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prakash.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public User findByEmail(String email);

}
