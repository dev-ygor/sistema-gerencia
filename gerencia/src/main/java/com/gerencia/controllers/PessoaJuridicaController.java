package com.gerencia.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.gerencia.entities.PessoaJuridica;
import com.gerencia.services.PessoaJuridicaService;
import com.gerencia.services.exceptions.ApiCepException;
import com.gerencia.services.exceptions.FornecedorInvalidoException;

@RestController
@RequestMapping(value = "api/fornecedorpj")
public class PessoaJuridicaController {
	
	@Autowired
	private PessoaJuridicaService juridicaService;
	
	@CrossOrigin(value = "http://localhost:3000")
	@PostMapping(value = "/validarCEP")
	public ResponseEntity<PessoaJuridica> procurar(@RequestBody PessoaJuridica cep) {
		
		try {
			return ResponseEntity.ok().body(juridicaService.validarCEP(cep));	
		}
		catch(ApiCepException e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@GetMapping(value = "/")
	public ResponseEntity<List<PessoaJuridica>> buscarTodos() {
		return ResponseEntity.ok().body(juridicaService.fornecedores());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<PessoaJuridica> buscarPorId(@PathVariable Long id) {
		PessoaJuridica fornecedor = juridicaService.buscarPorId(id);
		
		return ResponseEntity.ok().body(fornecedor);
	}
	
	@CrossOrigin(value = "http://localhost:3000")
	@PostMapping(value = "/cadastro")
	public ResponseEntity<PessoaJuridica> cadastro(@RequestBody PessoaJuridica pessoaJuridica) {
		
		try {
			
			PessoaJuridica obj = juridicaService.cadastro(pessoaJuridica);
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(obj.getId()).toUri();
			return ResponseEntity.created(uri).body(obj);
		
		}
		catch(FornecedorInvalidoException f) {
			
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<PessoaJuridica> atualizar(@PathVariable Long id, @RequestBody PessoaJuridica fornecedor) {
		return ResponseEntity.ok().body(juridicaService.atualizar(id, fornecedor));
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<PessoaJuridica> delete(@PathVariable Long id) {
		juridicaService.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/cadastro/empresa/{id}")
	public ResponseEntity<Void> cadastroFornecedorPF(@RequestBody PessoaJuridica fornecedor, @PathVariable Long id) {
		
		juridicaService.cadastroEmpresa(fornecedor, id);
		
		return ResponseEntity.noContent().build();
		
	}

}
