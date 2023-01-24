package com.paula.os.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.paula.os.domain.Technic;
import com.paula.os.dtos.TechnicDTO;
import com.paula.os.services.TechnicService;

@RestController
@RequestMapping(value= "/technics")
public class TechnicController {
	
	@Autowired
	private TechnicService technicService;
	
	
	@GetMapping(value= "/{id}")
	public ResponseEntity<TechnicDTO> findById(@PathVariable Integer id) {
				
		TechnicDTO objDTO = new TechnicDTO(technicService.findById(id));
		return ResponseEntity.ok().body(objDTO);
		
	}
	
	@GetMapping
	public ResponseEntity<List<TechnicDTO>> findAll(){
		List<TechnicDTO> listDTO = technicService.findAll()
				.stream().map(obj -> new TechnicDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	
	@PostMapping
	public ResponseEntity<TechnicDTO> create(@Valid @RequestBody TechnicDTO objDTO){
		Technic newObj = technicService.create(objDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(newObj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
		
	}
	
	@PutMapping(value= "/{id}")
	public ResponseEntity<TechnicDTO> update(@PathVariable Integer id, @Valid @RequestBody TechnicDTO objDTO){
		TechnicDTO newObj = new TechnicDTO(technicService.update(id, objDTO));
		return ResponseEntity.ok().body(newObj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		technicService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
