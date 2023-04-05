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

import com.gerencia.entities.Empresa;
import com.gerencia.services.EmpresaService;
import com.gerencia.services.exceptions.ApiCepException;
import com.gerencia.services.exceptions.EmpresaInvalidaException;


@RestController
@RequestMapping(value = "/api/empresa")
public class EmpresaController {
	
	@Autowired
	private EmpresaService empresaService;
	
	
	@PostMapping(value = "/validarCEP")
	@CrossOrigin(value = "http://localhost:3000")
	public ResponseEntity<Empresa> procurar(@RequestBody Empresa cep) {
		
		try {
			return ResponseEntity.ok().body(empresaService.validarCEP(cep));	
		}
		catch(ApiCepException e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	
	@PostMapping(value = "/cadastro")
	@CrossOrigin(value = "http://localhost:3000")
	public ResponseEntity<Empresa> cadastro(@RequestBody Empresa empresa) {
		
		try {
			Empresa obj = empresaService.cadastro(empresa);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(obj.getId()).toUri();
			return ResponseEntity.created(uri).body(obj);
		}
		catch(EmpresaInvalidaException e) {
			return ResponseEntity.badRequest().build();
		}
		
	}
	
	@GetMapping(value = "/")
	public ResponseEntity<List<Empresa>> buscarTodos() {
		return ResponseEntity.ok().body(empresaService.buscarTodos());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id) {
		Empresa empresa = empresaService.buscarPorId(id);
		
		return ResponseEntity.ok().body(empresa);
	}
	
	@PutMapping(value = "/cadastro/fornecedorPF/{id}")
	public ResponseEntity<Void> cadastroFornecedorPF(@RequestBody Empresa empresa, @PathVariable Long id) {
		
		empresaService.cadastroFornecedorPF(empresa, id);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping(value = "/cadastro/fornecedorPJ/{id}")
	public ResponseEntity<Void> cadastroFornecedorPJ(@RequestBody Empresa empresa, @PathVariable Long id) {
		
		empresaService.cadastroFornecedorPJ(empresa, id);
		
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping(value = "/atualizar/{id}")
	public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @RequestBody Empresa empresa) {
		return ResponseEntity.ok().body(empresaService.atualizar(id, empresa));
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		empresaService.delete(id);
		
		return ResponseEntity.noContent().build();
	}

} 
