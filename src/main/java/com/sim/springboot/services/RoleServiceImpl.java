package com.sim.springboot.services;

import java.util.Collection;
import java.util.Optional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sim.springboot.models.Role;
import com.sim.springboot.models.User;
import com.sim.springboot.repos.RoleRepo;
import com.sim.springboot.repos.UserRepo;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public Collection<Role> findAll() {
		return roleRepo.findAll();
	}
	
	@Override
	public Optional<Role> findById(int id) {
		return roleRepo.findById(id);
	}
	
	@Override
	public Role saveOrUpdate(Role role) {
		return roleRepo.saveAndFlush(role);
	}
	
	@Override
	public String deleteById(int id) {
		JSONObject jsonObject = new JSONObject();
		try {
			roleRepo.deleteById(id);
			jsonObject.put("message", "Role deleted succesfully !");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return jsonObject.toString();
		
	}
}
