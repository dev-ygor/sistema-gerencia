package com.gerencia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gerencia.entities.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
	
	
	Boolean existsByCNPJ(String cnpj);
	
	Optional<Empresa> findByCNPJ(String cnpj);
}
