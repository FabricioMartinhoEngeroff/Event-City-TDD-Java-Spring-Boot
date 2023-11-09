package com.devFabricio.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.devFabricio.bds02.dto.CityDTO;
import com.devFabricio.bds02.entities.City;
import com.devFabricio.bds02.repositories.CityRepository;
import com.devFabricio.bds02.service.exceptio.DatabaseException;
import com.devFabricio.bds02.service.exceptio.ResourceNotFoundExceptions;


@Service
public class CityService {

	@Autowired
	public CityRepository repository;
	
	public List<CityDTO> findAll(){
		List<City> list = repository.findAll(Sort.by("name"));
		return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public CityDTO insert(CityDTO dto) {
			City entity = new City();
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CityDTO(entity);
	}
	 
	public void delete(Long id) {
		try {
		repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundExceptions("Id not found " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
			
		}
	}

	public static City getCity(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
