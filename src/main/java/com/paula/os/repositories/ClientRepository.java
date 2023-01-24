package com.paula.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.paula.os.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{

	@Query("SELECT obj FROM Client obj WHERE obj.cpf =:cpf")
	Client findByCPF(@Param("cpf") String cpf);

	
	
}
