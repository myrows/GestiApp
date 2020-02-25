package com.salesianostriana.dam.gestiapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.gestiapp.model.RoomCategory;

@Repository
public interface RoomCategoryRepository extends JpaRepository<RoomCategory, Long> {

}
