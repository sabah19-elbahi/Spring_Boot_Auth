package com.sim.springboot.control;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sim.springboot.config.JwtTokenProvider;
import com.sim.springboot.models.User;
import com.sim.springboot.repos.UserRepo;
import com.sim.springboot.services.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api")
public class UserResource {
	
	private static Logger log = LoggerFactory.getLogger(UserResource.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	

	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepo userRepo;
	
	@PostMapping(value="/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@CrossOrigin
	public ResponseEntity<String> authenticate(@RequestBody User user) {
		log.info("UserResource : login");
		JSONObject jsonObj = new JSONObject();
		
		try {
			
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
			if(authentication.isAuthenticated()) {
				String email = user.getEmail();
				jsonObj.put("name", userRepo.findByEmail(authentication.getName()).getName());
				if(userRepo.findByEmail(authentication.getName()).getRoleId()==1) {
					jsonObj.put("role", "ADMIN");
				}
				else {
					jsonObj.put("role", "USER");
				}
				
				jsonObj.put("token", tokenProvider.createToken(email, userRepo.findByEmail(email).getRole()));
				return new ResponseEntity<String>(jsonObj.toString(), HttpStatus.OK);
			}
			
		} catch(JSONException e) {
			try {
				jsonObj.put("exception", e.getMessage());
			}
			catch (JSONException e1) {
				e1.printStackTrace();
			}
			return new ResponseEntity<String>(jsonObj.toString(), HttpStatus.UNAUTHORIZED);
		}
		return null;
	}
	
	
	
	
	
	@PostMapping(value="/register", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	@CrossOrigin
	public ResponseEntity<String> register(@RequestBody User user) {
		log.info("UserResource : register");
		JSONObject jsonObj = new JSONObject();
		try {
			
				userDetailsServiceImpl.saveUser(user);
				jsonObj.put("message", "User Registered Successfully.");
				return new ResponseEntity<String>(jsonObj.toString(), HttpStatus.OK);
			
		} catch(JSONException | IllegalArgumentException e) {
			try {
				jsonObj.put("exception", e.getMessage());
			}
			catch (JSONException e1) {
				e1.printStackTrace();
			}
			return new ResponseEntity<String>(jsonObj.toString(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	
	
	
	
	
	
	
}
