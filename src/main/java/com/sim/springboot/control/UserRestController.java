package com.sim.springboot.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sim.springboot.models.User;
import com.sim.springboot.repos.RoleRepo;
import com.sim.springboot.repos.UserRepo;



@RestController
@RequestMapping("/api/users")
public class UserRestController {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional
	@CrossOrigin
	public String saveUser(@ModelAttribute User user) {
		userRepo.save(user);
		user.getId();
		return "Saved customer with id : "+user.getId();
	}
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional
	@CrossOrigin
	public String updateUser(@ModelAttribute User user,@PathVariable int id) {
		Optional<User> cs = userRepo.findById(id);
		
		if (cs.isPresent()) {
		      User _user = cs.get();
		      _user.setId(id);
		      _user.setName(user.getName());
		      _user.setEmail(user.getEmail());
		      _user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		      _user.setRole(roleRepo.getById(_user.getRoleId()));
		      userRepo.save(_user);
		      return "Updated user with id : "+user.getId();
		    } else {
		    	return "User with id : "+user.getId()+" Not found ! ";
		    }
		
	}
	
	@GetMapping("")
	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional
	@CrossOrigin
	public List<User> getAll(){
		return userRepo.findAll();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	@Transactional
	@CrossOrigin
	public Optional<User> getUser(@PathVariable int id){
		return userRepo.findById(id);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional
	@CrossOrigin
	public String deleteUser(@PathVariable int id) {
		userRepo.deleteById(id);
		System.out.println("trying to delete user: "+id);
		return "Deleted user with id : "+id;
	}
	
}
