package com.salesianostriana.dam.gestiapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.gestiapp.model.Room;
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
