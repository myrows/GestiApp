package com.salesianostriana.dam.gestiapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.gestiapp.model.ReservedDate;
import com.salesianostriana.dam.gestiapp.repository.ReservedDateRepository;
import com.salesianostriana.dam.gestiapp.service.base.BaseService;

/**
 * Clase servicio que gestiona días festivos
 * 
 * @author José María, Jesús Ceacero, José Manuel, Daniel Troncoso
 */

@Service
public class ReservedDateService extends BaseService<ReservedDate, Long, ReservedDateRepository> {

	@Override
	public ReservedDate save(ReservedDate t) {
		// TODO Auto-generated method stub
		return super.save(t);
	}

	@Override
	public ReservedDate edit(ReservedDate t) {
		// TODO Auto-generated method stub
		return super.edit(t);
	}

	@Override
	public void delete(ReservedDate t) {
		// TODO Auto-generated method stub
		super.delete(t);
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		super.deleteById(id);
	}

	@Override
	public ReservedDate findById(Long id) {
		// TODO Auto-generated method stub
		return super.findById(id);
	}

	@Override
	public List<ReservedDate> findAll() {
		// TODO Auto-generated method stub
		return super.findAll();
	}

}
