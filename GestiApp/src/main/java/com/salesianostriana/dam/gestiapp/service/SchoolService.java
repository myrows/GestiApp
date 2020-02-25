package com.salesianostriana.dam.gestiapp.service;

import org.springframework.stereotype.Service;

import com.salesianostriana.dam.gestiapp.model.School;
import com.salesianostriana.dam.gestiapp.repository.SchoolRepository;
import com.salesianostriana.dam.gestiapp.service.base.BaseService;

@Service
public class SchoolService extends BaseService<School, Long, SchoolRepository> {

}
