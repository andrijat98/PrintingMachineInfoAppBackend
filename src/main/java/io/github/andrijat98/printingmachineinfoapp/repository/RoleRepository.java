package io.github.andrijat98.printingmachineinfoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.andrijat98.printingmachineinfoapp.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByName(String name);
}
