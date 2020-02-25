package com.salesianostriana.dam.gestiapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.gestiapp.model.AppUser;
import com.salesianostriana.dam.gestiapp.repository.AppUserRepository;
import com.salesianostriana.dam.gestiapp.service.base.BaseService;

/**
 * Clase servicio que gestiona usuarios
 * 
 * @author José María, Jesús Ceacero, José Manuel, Daniel Troncoso
 */
@Service
public class AppUserService extends BaseService<AppUser, Long, AppUserRepository> {

	
	@Autowired
	AppUserRepository userRepository;

	
	public AppUser searchByEmail (String email) {
		return userRepository.findFirstByUserEmail(email);
		
	}
	
	public List<AppUser> searchByValitadedFalse () {
		return userRepository.findAllByValidatedFalse();
		
	}
	
	@Override
	public AppUser save(AppUser t) {
		// TODO Auto-generated method stub
		return super.save(t);
	}

	@Override
	public AppUser edit(AppUser t) {
		// TODO Auto-generated method stub
		return super.edit(t);
	}

	@Override
	public void delete(AppUser t) {
		// TODO Auto-generated method stub
		super.delete(t);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		super.deleteById(id);
	}

	@Override
	public AppUser findById(Long id) {
		// TODO Auto-generated method stub
		return super.findById(id);
	}

	@Override
	public List<AppUser> findAll() {
		// TODO Auto-generated method stub
		return super.findAll();
	}
	
	public Page<AppUser> findAllPageable(Pageable pageable) {
        
		 return userRepository.findAll(pageable);
	}
	
	
	
}
