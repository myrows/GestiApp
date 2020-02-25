package com.salesianostriana.dam.gestiapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesianostriana.dam.gestiapp.model.School;
@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

}
