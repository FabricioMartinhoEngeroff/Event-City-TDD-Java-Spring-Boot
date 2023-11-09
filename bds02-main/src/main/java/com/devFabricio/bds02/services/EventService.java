package com.devFabricio.bds02.services;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devFabricio.bds02.dto.EventDTO;
import com.devFabricio.bds02.entities.City;
import com.devFabricio.bds02.entities.Event;
import com.devFabricio.bds02.repositories.CityRepository;
import com.devFabricio.bds02.repositories.EventRepository;
import com.devFabricio.bds02.service.exceptio.ResourceNotFoundExceptions;

@Service
public class EventService {

	@Autowired
	public EventRepository repository;
	
	@Autowired
	private CityRepository cityRepository;
	
	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
	    try {
	        Event entity = repository.getOne(id);
	        entity.setName(dto.getName());
	        entity.setDate(dto.getDate());
	        entity.setUrl(dto.getUrl());

	        City city = cityRepository.getOne(dto.getCityId()); 
	        entity.setCity(city);

	        entity = repository.save(entity);
	        return new EventDTO(entity);
	    }
	    catch (EntityNotFoundException e) {
            throw new ResourceNotFoundExceptions("Id not found " + id);
        }
	
	}
}
