package com.gerencia.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.gerencia.entities.Empresa;
import com.gerencia.entities.PessoaJuridica;
import com.gerencia.repositories.EmpresaRepository;
import com.gerencia.repositories.PessoaJuridicaRepository;
import com.gerencia.services.exceptions.ApiCepException;
import com.gerencia.services.exceptions.EmpresaInvalidaException;
import com.gerencia.services.exceptions.FornecedorInvalidoException;
import com.gerencia.services.exceptions.FornecedorNaoEncontradException;

import reactor.core.publisher.Mono;

@Service
public class PessoaJuridicaService {
	
	@Autowired
	private PessoaJuridicaRepository juridicaDAO;
	
	@Autowired
	private EmpresaRepository empresaDAO;
	
	private final WebClient webClient;
	
	public PessoaJuridicaService(WebClient.Builder builder) {
		webClient = builder.baseUrl("http://cep.la/").build();
	}
	
	public PessoaJuridica validarCEP(PessoaJuridica cep) {
	
		Mono<PessoaJuridica> endereco = webClient
				.get()
				.uri(cep.getCep())
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(HttpStatusCode::is4xxClientError, response -> {
		            return Mono.error(new ApiCepException("CEP não encontrado"));
		        })
				.onStatus(HttpStatusCode::is5xxServerError, response -> {
					return Mono.error(new ApiCepException("Erro inesperado"));
				})
				.bodyToMono(PessoaJuridica.class)
				.onErrorResume(error -> {
					if(error instanceof ApiCepException) {
						throw new ApiCepException("CEP Inválido");
					}
					else {
						throw new ApiCepException("Erro inesperado");
					}
				});
				
		return endereco.block();
	
	}
	
	public PessoaJuridica cadastro(PessoaJuridica pessoaJuridica) {
		
		if(juridicaDAO.existsByCNPJ(pessoaJuridica.getCNPJ())) {
			throw new FornecedorInvalidoException("CNPJ já existente");
		}
		
		try {
			
		PessoaJuridica endereco = validarCEP(pessoaJuridica);
		
		pessoaJuridica.setBairro(endereco.getBairro());
		pessoaJuridica.setCidade(endereco.getCidade());
		pessoaJuridica.setLogradouro(endereco.getLogradouro());
		pessoaJuridica.setUf(endereco.getUf());
		
		return juridicaDAO.save(pessoaJuridica);
		
		}
		catch(ApiCepException e) {
			throw new EmpresaInvalidaException("CEP inválido");
		}
	
	}
	
	public List<PessoaJuridica> fornecedores() {
		return juridicaDAO.findAll();
	}
	
	public PessoaJuridica buscarPorId(Long id) {
		Optional<PessoaJuridica> fornecedor = juridicaDAO.findById(id);
		
		return fornecedor.orElseThrow(() -> new FornecedorNaoEncontradException("Fornecedor não encotrado"));
	}
	
	public void delete(Long id) {
		
		Optional<PessoaJuridica> fornecedor = juridicaDAO.findById(id);
		
		juridicaDAO.delete(fornecedor.get());
	}
	
	public PessoaJuridica atualizar(Long id, PessoaJuridica fornecedor) {
		
		Optional<PessoaJuridica> entity = juridicaDAO.findById(id);
		atualizarDados(entity.get(), fornecedor);
		
		return juridicaDAO.save(entity.get());
	}
	
	private void atualizarDados(PessoaJuridica entity, PessoaJuridica fornecedor) {
		
		entity.setComplemento(fornecedor.getComplemento());
		entity.setEmail(fornecedor.getEmail());
		entity.setNome(fornecedor.getNome());
	}
	
	public void cadastroEmpresa(PessoaJuridica fornecedor, Long id) {
		
		Optional<PessoaJuridica> fornecedorAux = juridicaDAO.findByCNPJ(fornecedor.getCNPJ());
		Optional<Empresa> empresaAux = empresaDAO.findById(id);
		
        
		fornecedorAux.get().getEmpresas().add(empresaAux.get());
		empresaAux.get().getPessoasJuridica().add(fornecedorAux.get());
		
		juridicaDAO.save(fornecedorAux.get());
		empresaDAO.save(empresaAux.get());
	}

}
