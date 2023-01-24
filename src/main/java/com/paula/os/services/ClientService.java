package com.paula.os.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paula.os.controller.exceptions.DataIntegratyViolationException;
import com.paula.os.domain.Client;
import com.paula.os.dtos.ClientDTO;
import com.paula.os.repositories.ClientRepository;
import com.paula.os.services.exceptions.ObjectNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	
	public Client findById(Integer id) {
		Optional<Client> obj = clientRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found! "
				+ "Id: " + id + ", "
				+ "Type: " + Client.class.getName()));
		
	}


	public List<Client> findAll() {
		return clientRepository.findAll();
	}
	
	public Client create(ClientDTO objDTO) {
		if(findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF already exists in database!");
		}
		
		return clientRepository.save(new Client(null, objDTO.getName(), objDTO.getCpf(), objDTO.getPhone()));
	}
	
	
	public Client update(Integer id, @Valid ClientDTO objDTO) {
		Client oldObj = findById(id);
		
		if(findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF already exists in database!");
		}
		
		oldObj.setName(objDTO.getName());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setPhone(objDTO.getPhone());
		
		return clientRepository.save(oldObj);
		
	}
	
	
	public void delete(Integer id) {
		Client obj = findById(id);
		
		if(obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("This person has service orders. It cannot be deleted!");
		}
		
		clientRepository.deleteById(id);
		
	}
	
	private Client findByCPF(ClientDTO objDTO) {
		Client obj = clientRepository.findByCPF(objDTO.getCpf());
		
		if(obj != null) {
			return obj;
		}
		
		return null;
	}


	

	
	
}
