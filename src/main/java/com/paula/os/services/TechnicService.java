package com.paula.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paula.os.controller.exceptions.DataIntegratyViolationException;
import com.paula.os.domain.Technic;
import com.paula.os.dtos.TechnicDTO;
import com.paula.os.repositories.TechnicRepository;
import com.paula.os.services.exceptions.ObjectNotFoundException;

@Service
public class TechnicService {

	@Autowired
	private TechnicRepository technicRepository;
	
	
	public Technic findById(Integer id) {
		Optional<Technic> obj = technicRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found! "
				+ "Id: " + id + ", "
				+ "Type: " + Technic.class.getName()));
		
	}


	public List<Technic> findAll() {
		return technicRepository.findAll();
	}
	
	public Technic create(TechnicDTO objDTO) {
		if(findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF already exists in database!");
		}
		
		return technicRepository.save(new Technic(null, objDTO.getName(), objDTO.getCpf(), objDTO.getPhone()));
	}
	
	
	public Technic update(Integer id, @Valid TechnicDTO objDTO) {
		Technic oldObj = findById(id);
		
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF already exists in database!");
		}
		
		oldObj.setName(objDTO.getName());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setPhone(objDTO.getPhone());
		
		return technicRepository.save(oldObj);
		
	}
	
	
	public void delete(Integer id) {
		Technic obj = findById(id);
		
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("This technician has service orders. It cannot be deleted!");
		}
		
		technicRepository.deleteById(id);
		
	}
	
	private Technic findByCPF(TechnicDTO objDTO) {
		Technic obj = technicRepository.findByCPF(objDTO.getCpf());
		
		if(obj != null) {
			return obj;
		}
		
		return null;
	}


	

	
	
}
