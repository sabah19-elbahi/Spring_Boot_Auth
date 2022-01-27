package com.sim.springboot.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sim.springboot.models.Role;
import com.sim.springboot.models.User;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer>{

}
