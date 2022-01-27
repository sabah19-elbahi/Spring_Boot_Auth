package com.sim.springboot.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sim.springboot.models.User;
import com.sim.springboot.repos.RoleRepo;
import com.sim.springboot.repos.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("Email "+email+" not found !");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getGrantedAuthority(user));
	}
	
	private Collection<GrantedAuthority> getGrantedAuthority(User user){
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		if(user.getRole().getName().equalsIgnoreCase("admin")) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			
		}
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}
	
	public User saveUser(User user) {
		User newUser = new User();
		newUser.setName(user.getName());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		newUser.setRole(roleRepo.getById(2));
		userRepo.save(newUser);
		return newUser;
	}

}
