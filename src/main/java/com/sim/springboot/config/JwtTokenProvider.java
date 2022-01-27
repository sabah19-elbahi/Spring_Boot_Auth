package com.sim.springboot.config;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.sim.springboot.models.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4971771984746732310L;

	@Value("${jwt.secret-key}")
	private String secretKey;
	
	void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	private long validityInMs = 86400000; //24 hours
	
	public String createToken(String email, Role role) {
		Claims claims = Jwts.claims().setSubject(email);
		claims.put("auth", role);
		
		Date now = new Date();
		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(new Date(now.getTime()+validityInMs)).signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	public Authentication getAuthentication(String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
		
	}
	
	public Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public long getValidityInMs() {
		return validityInMs;
	}

	public void setValidityInMs(long validityInMs) {
		this.validityInMs = validityInMs;
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
