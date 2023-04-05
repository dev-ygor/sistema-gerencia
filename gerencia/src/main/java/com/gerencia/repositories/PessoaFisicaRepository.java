package com.gerencia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gerencia.entities.PessoaFisica;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {
	
	Boolean existsByCpf(String cpf);
	
	Optional<PessoaFisica> findByCpf(String cpf);

}
