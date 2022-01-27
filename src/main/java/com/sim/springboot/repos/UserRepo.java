package com.sim.springboot.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sim.springboot.models.Role;
import com.sim.springboot.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
	@Query("FROM User WHERE email=:email")
	User findByEmail(@Param("email") String email);
}
