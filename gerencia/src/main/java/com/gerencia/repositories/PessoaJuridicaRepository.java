package com.gerencia.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gerencia.entities.PessoaJuridica;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, Long> {
	
	Boolean existsByCNPJ(String cnpj);

	Optional<PessoaJuridica> findByCNPJ(String cnpj);
}
