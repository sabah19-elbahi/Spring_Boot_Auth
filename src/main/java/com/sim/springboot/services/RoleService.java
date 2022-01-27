package com.sim.springboot.services;

import java.util.Collection;
import java.util.Optional;

import com.sim.springboot.models.Role;

public interface RoleService {

	Collection<Role> findAll();

	Optional<Role> findById(int id);

	Role saveOrUpdate(Role role);

	String deleteById(int id);

}
