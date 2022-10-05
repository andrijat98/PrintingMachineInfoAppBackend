package io.github.andrijat98.printingmachineinfoapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.andrijat98.printingmachineinfoapp.models.AppUser;
import io.github.andrijat98.printingmachineinfoapp.models.Role;
import io.github.andrijat98.printingmachineinfoapp.repository.AppUserRepository;
import io.github.andrijat98.printingmachineinfoapp.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserService implements UserDetailsService{
	
	
	private final AppUserRepository userRepo;
	private final RoleRepository roleRepo;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepo.findByUsername(username);
		
		if(user == null) {
			log.debug(username);
			log.error("User not found");
			throw new UsernameNotFoundException("User not found");
		} else {
			log.info("User found: {}", username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
		
		return new User(user.getUsername(), user.getPassword(), authorities);
	}
	
	public AppUser saveUser(AppUser user) {
		log.info("Saving user {} to the database.", user.getName());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}
	
	public Role saveRole(Role role) {
		log.info("Saving role {} to the database.", role.getName());
		return roleRepo.save(role);
	}
	
	public void addRoleToUser(String username, String roleName) {
		log.info("Adding role {} to user {}.", roleName, username);
		AppUser user = userRepo.findByUsername(username);
		Role role = roleRepo.findByName(roleName);
		user.getRoles().add(role);
	}
	
	public AppUser getUser(String username) {
		log.info("Getting user {}", username);
		return userRepo.findByUsername(username);
	}
	
	public List<AppUser> getUsers() {
		log.info("Getting all users");
		return userRepo.findAll();
	}

}
