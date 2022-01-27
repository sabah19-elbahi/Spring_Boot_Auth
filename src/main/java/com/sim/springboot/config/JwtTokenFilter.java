package com.sim.springboot.config;

import java.io.IOException;
import java.util.Date;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtTokenProvider tokenProvider;
	
	private static Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);




	public JwtTokenFilter(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}


	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods",	"GET,HEAD,OPTIONS,POST,PUT,DELETE");
		response.addHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers,Origin,Accept, "
				+ "X-Requested-With, Content-Type, Access-Control-Request-Method,	"
				+ "Access-Control-Request-Headers, Authorization");
		response.addHeader("Access-Control-Expose-Headers","Authorization, Access-ControlAllow-Origin,Access-Control-Allow-Credentials ");
		
		
		log.info("JwtTokenFilter : doFilterInternal");
		String token = request.getHeader("Authorization");
		System.out.println(token);
		if (token != null) {
			try {
				Claims claims = tokenProvider.getClaimsFromToken(token);
				if (!claims.getExpiration().before(new Date())) {
					Authentication authentication = tokenProvider.getAuthentication(claims.getSubject());
					if (authentication.isAuthenticated()) {
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			} catch (RuntimeException e) {
				try {
					SecurityContextHolder.clearContext();
					response.setContentType("application/json");
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.getWriter().println(
							new JSONObject().put("exception", "expired or invalid JWT token " + e.getMessage()));
				} catch (IOException | JSONException e1) {
					e1.printStackTrace();
				}
				return;
			}
		} else {
			log.info("first time so creating token using UserResourceImpl - authenticate method");
		}
		filterChain.doFilter(request, response);
	}

}
