package io.github.andrijat98.printingmachineinfoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.andrijat98.printingmachineinfoapp.models.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{

	AppUser findByUsername(String username);
	
}
