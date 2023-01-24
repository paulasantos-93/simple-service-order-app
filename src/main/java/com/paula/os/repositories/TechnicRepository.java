package com.paula.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paula.os.domain.Technic;

@Repository
public interface TechnicRepository extends JpaRepository<Technic, Integer>{

	@Query("SELECT obj FROM Technic obj WHERE obj.cpf =:cpf")
	Technic findByCPF(@Param("cpf") String cpf);

	
	
	
}
