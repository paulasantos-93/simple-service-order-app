package com.paula.os.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paula.os.domain.Client;
import com.paula.os.domain.OS;
import com.paula.os.domain.Technic;
import com.paula.os.domain.enums.Priority;
import com.paula.os.domain.enums.Status;
import com.paula.os.dtos.OSDTO;
import com.paula.os.repositories.OSRepository;
import com.paula.os.services.exceptions.ObjectNotFoundException;

@Service
public class OSService {

	@Autowired
	private OSRepository osRepository;
	
	@Autowired
	private TechnicService technicService;
	
	@Autowired
	private ClientService clientService;
	
	
	public OS findById(Integer id) {
		Optional<OS> obj = osRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id 
				+ ", Type: " + OS.class.getName()));	
	}
	
	public List<OS> findAll() {
		return osRepository.findAll();
	}

	public OS create(@Valid OSDTO obj) {
		return fromDTO(obj);
	}
	
	public OS update(@Valid OSDTO obj) {
		findById(obj.getId());
		return fromDTO(obj);
	}
	
	private OS fromDTO(OSDTO obj) {
		OS newObj = new OS();
		newObj.setId(obj.getId());
		newObj.setObservations(obj.getObservations());
		newObj.setPriority(Priority.toEnum(obj.getPriority()));
		newObj.setStatus(Status.toEnum(obj.getStatus()));
		
		Technic tec = technicService.findById(obj.getTechnic());
		Client cli = clientService.findById(obj.getClient());
		
		newObj.setTechnic(tec);
		newObj.setClient(cli);
		
		if(newObj.getStatus().getCod().equals(2)) {
			newObj.setCloseDate(LocalDateTime.now());
		}
		
		return osRepository.save(newObj);	
	}

	
}
