package com.sim.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sim.springboot.models.Role;
import com.sim.springboot.models.User;
import com.sim.springboot.services.RoleService;
import com.sim.springboot.services.UserService;

@SpringBootApplication

public class AuthApplication implements CommandLineRunner {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedHeaders("*").allowedOrigins("*").allowedMethods("*").allowCredentials(true);
			}
		};
	}
	
	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Role role = new Role();
		//role.setName("ADMIN");
		//roleService.saveOrUpdate(role);
		
		//Role role_2 = new Role();
		//role_2.setName("USER");
		//roleService.saveOrUpdate(role_2);
		
		/*
		 * User user = new User(); user.setEmail("admin@admin.com");
		 * user.setName("admin"); user.setPassword(new
		 * BCryptPasswordEncoder().encode("123456"));
		 * user.setRole(roleService.findById(1).get()); userService.saveOrUpdate(user);
		 * 
		 * User user_2 = new User(); user_2.setEmail("user@user.com");
		 * user_2.setName("user"); user_2.setPassword(new
		 * BCryptPasswordEncoder().encode("123456"));
		 * user_2.setRole(roleService.findById(2).get());
		 * userService.saveOrUpdate(user_2);
		 */
	}

}
