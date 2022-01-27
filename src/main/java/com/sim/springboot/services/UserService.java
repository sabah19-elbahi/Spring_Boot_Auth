package com.sim.springboot.services;

import java.util.Collection;
import java.util.Optional;

import com.sim.springboot.models.User;

public interface UserService {

	Collection<User> findAll();

	Optional<User> findById(int id);

	User saveOrUpdate(User user);

	String deleteById(int id);
	
	

}
