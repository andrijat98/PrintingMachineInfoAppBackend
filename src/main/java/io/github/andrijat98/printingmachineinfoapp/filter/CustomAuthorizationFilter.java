package io.github.andrijat98.printingmachineinfoapp.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(request.getServletPath().equals("/login") || request.getServletPath().equals("/user/token/refresh")) {
			filterChain.doFilter(request, response);
		}
		else {
			String authorizationHeader = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				try {
					
					String token = authorizationHeader.substring(7);
					Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
 									
					Stream<String> rolesStream = Stream.of(roles);
					rolesStream.forEach(role -> {
						authorities.add(new SimpleGrantedAuthority(role));
					});
					
					UsernamePasswordAuthenticationToken authenticationToken =
							new UsernamePasswordAuthenticationToken(username, null, authorities);
					
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
				} catch (Exception e) {
					log.error("Error logging in: {}", e.getMessage());
					response.setHeader("error", e.getMessage());
					response.setStatus(org.springframework.http.HttpStatus.FORBIDDEN.value());
					//response.sendError(org.springframework.http.HttpStatus.FORBIDDEN.value());
					
					Map<String, String> error = new HashMap<>();
					error.put("error_message", e.getMessage());
					response.setContentType("application/json");
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}

			} else {
				filterChain.doFilter(request, response);
			}
		}
		
	}

}