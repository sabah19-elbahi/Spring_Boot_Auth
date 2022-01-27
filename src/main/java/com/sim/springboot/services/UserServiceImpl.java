package com.sim.springboot.services;

import java.util.Collection;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.springboot.models.User;
import com.sim.springboot.repos.UserRepo;

@Service
public class UserServiceImpl implements UserService {

		@Autowired
		private UserRepo userRepo;
		
		@Override
		public Collection<User> findAll() {
			return userRepo.findAll();
		}
		
		@Override
		public Optional<User> findById(int id) {
			return userRepo.findById(id);
		}
		
		@Override
		public User saveOrUpdate(User user) {
			return userRepo.saveAndFlush(user);
		}
		
		@Override
		public String deleteById(int id) {
			JSONObject jsonObject = new JSONObject();
			try {
				userRepo.deleteById(id);
				jsonObject.put("message", "User deleted succesfully !");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return jsonObject.toString();
			
		}
		
		
}
