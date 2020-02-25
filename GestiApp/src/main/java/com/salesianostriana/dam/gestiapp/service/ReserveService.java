package com.salesianostriana.dam.gestiapp.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesianostriana.dam.gestiapp.model.Reserve;
import com.salesianostriana.dam.gestiapp.model.Room;
import com.salesianostriana.dam.gestiapp.model.RoomCategory;
import com.salesianostriana.dam.gestiapp.repository.ReserveRepository;
import com.salesianostriana.dam.gestiapp.service.base.BaseService;

/**
 * Clase servicio que gestiona reservas
 * 
 * @author José María, Jesús Ceacero, José Manuel, Daniel Troncoso
 */
@Service
public class ReserveService extends BaseService<Reserve, Long, ReserveRepository> {

	@Autowired
	ReserveRepository reserveRepository;
	@Autowired
	RoomService roomService;
	
	public List<Reserve> findAll(long id){
		return reserveRepository.findAll();
	}
	
	public List<Reserve> findAllByReserveUserId(long id){
		return reserveRepository.findAllByReserveUserId(id);
	}
	
	public List<Room> findByCategory(RoomCategory roomCategory){
		List<Room> findCategory = new ArrayList<Room>();
		
		for (Room room : roomService.findAll()) {
			if(room.getRoomCategory().equals(roomCategory)) {
				findCategory.add(room);
			}
			
		}
		return findCategory;
	}
	
	
	public List<Reserve> findByDate(LocalDate date, LocalTime time){
		List<Reserve> findDate = new ArrayList<Reserve>();
		
		for (Reserve reserve : reserveRepository.findAll()) {
			if(date.equals(reserve.getDate()) && time.equals(reserve.getTimeZone().getTime())) {
				findDate.add(reserve);
			}
		}
		return findDate;
	}
	
	public List<Room> findByReserve(LocalDate date, LocalTime time, RoomCategory roomCategory){
		List<Room> findReserve = findByCategory(roomCategory);
		
		for (Room room : findByCategory(roomCategory)) {
			for (Reserve reserve : findByDate(date,time)) {
			
				if(room.equals(reserve.getReservedRoom())) {
					findReserve.remove(room);
				}
			}
		}
		
		return findReserve;
		
	}
	
	
}
