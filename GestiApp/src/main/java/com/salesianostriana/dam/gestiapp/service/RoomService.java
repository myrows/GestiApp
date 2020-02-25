package com.salesianostriana.dam.gestiapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.gestiapp.model.Room;
import com.salesianostriana.dam.gestiapp.repository.RoomRepository;
import com.salesianostriana.dam.gestiapp.service.base.BaseService;

/**
 * Clase servicio que gestiona las aulas
 * 
 * @author José María, Jesús Ceacero, José Manuel, Daniel Troncoso
 */

@Service
public class RoomService extends BaseService<Room, Long, RoomRepository> {
	
	@Autowired
	private RoomRepository roomRepository;
	
	public Page<Room> findAllPageable(Pageable pageable) {
        
		 return roomRepository.findAll(pageable);
	}
}
