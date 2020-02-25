package com.salesianostriana.dam.gestiapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.gestiapp.model.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {
	
	public List<Reserve> findAllByReserveUserId(long id);

}
