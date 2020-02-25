package com.salesianostriana.dam.gestiapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.gestiapp.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	public AppUser findFirstByUserEmail(String username);
	
	public List<AppUser> findAllByValidatedFalse();
}
