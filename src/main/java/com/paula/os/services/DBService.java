package com.paula.os.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paula.os.domain.Client;
import com.paula.os.domain.OS;
import com.paula.os.domain.Technic;
import com.paula.os.domain.enums.Priority;
import com.paula.os.domain.enums.Status;
import com.paula.os.repositories.ClientRepository;
import com.paula.os.repositories.OSRepository;
import com.paula.os.repositories.TechnicRepository;

@Service
public class DBService {

	@Autowired
	private TechnicRepository technicRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private OSRepository osRepository;
	
	
	public void instanceDB() {
		
		Technic t1 = new Technic(null, "Valdir Cezar", "902.453.770-34", "(88) 98888-8888");
		Client c1 = new Client(null, "Betina Campos", "902.844.240-56", "(42) 99941-7698");
		OS os1 = new OS(null, Priority.HIGH, "Test create OS", Status.IN_COURSE, t1, c1);
		
		t1.getList().add(os1);
		c1.getList().add(os1);
		
		technicRepository.save(t1);
		clientRepository.save(c1);
		osRepository.save(os1);
	}
	
}
