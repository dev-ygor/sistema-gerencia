package com.gerencia.services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.gerencia.entities.Empresa;
import com.gerencia.entities.PessoaFisica;
import com.gerencia.repositories.EmpresaRepository;
import com.gerencia.repositories.PessoaFisicaRepository;
import com.gerencia.services.exceptions.ApiCepException;
import com.gerencia.services.exceptions.EmpresaInvalidaException;
import com.gerencia.services.exceptions.FornecedorInvalidoException;
import com.gerencia.services.exceptions.FornecedorNaoEncontradException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class PessoaFisicaService {
	
	@Autowired
	private PessoaFisicaRepository pessoaFisicaDAO;
	
	@Autowired
	private EmpresaRepository empresaDAO;
	
	private final WebClient webClient;
	
	public PessoaFisicaService(WebClient.Builder builder) {
		webClient = builder.baseUrl("http://cep.la/").build();
	}
	
	public PessoaFisica validarCEP(PessoaFisica cep) {
	
		Mono<PessoaFisica> endereco = webClient
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
				.bodyToMono(PessoaFisica.class)
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
	
	public PessoaFisica cadastro(PessoaFisica pessoaFisica) {
		
		if(pessoaFisicaDAO.existsByCpf(pessoaFisica.getCpf())) {
			throw new FornecedorInvalidoException("CPF já existente");
		}
		
		try {
			
		PessoaFisica endereco = validarCEP(pessoaFisica);
		
		pessoaFisica.setBairro(endereco.getBairro());
		pessoaFisica.setCidade(endereco.getCidade());
		pessoaFisica.setLogradouro(endereco.getLogradouro());
		pessoaFisica.setUf(endereco.getUf());
		
		return pessoaFisicaDAO.save(pessoaFisica);
		
		}
		catch(ApiCepException e) {
			throw new EmpresaInvalidaException("CEP inválido");
		}
	
	}
	
	public List<PessoaFisica> fornecedores() {
		return pessoaFisicaDAO.findAll();
	}
	
	public PessoaFisica buscarPorId(Long id) {
		Optional<PessoaFisica> fornecedor = pessoaFisicaDAO.findById(id);
		
		return fornecedor.orElseThrow(() -> new FornecedorNaoEncontradException("Fornecedor não encotrado"));
	}
	
	public void delete(Long id) {
		
		Optional<PessoaFisica> fornecedor = pessoaFisicaDAO.findById(id);
		
		pessoaFisicaDAO.delete(fornecedor.get());
	}
	
	public PessoaFisica atualizar(Long id, PessoaFisica fornecedor) {
		
		Optional<PessoaFisica> entity = pessoaFisicaDAO.findById(id);
		atualizarDados(entity.get(), fornecedor);
		
		return pessoaFisicaDAO.save(entity.get());
	}
	
	private void atualizarDados(PessoaFisica entity, PessoaFisica fornecedor) {
		
		entity.setComplemento(fornecedor.getComplemento());
		entity.setNome(fornecedor.getNome());
		entity.setEmail(fornecedor.getEmail());
		entity.setNascimento(fornecedor.getNascimento());
	}
	
	public void cadastroEmpresa(PessoaFisica fornecedor, Long id) {
		
		Optional<PessoaFisica> fornecedorAux = pessoaFisicaDAO.findByCpf(fornecedor.getCpf());
		Optional<Empresa> empresaAux = empresaDAO.findById(id);
		
		// Verificar se empresa é do PR e o fornecedor é menor de idade
		ZoneId timeZone = ZoneId.systemDefault();
        LocalDate getNascimento = fornecedorAux.get().getNascimento()
        							.toInstant().atZone(timeZone)
        							.toLocalDate();
        int anoNascimento = getNascimento.getYear();
        
        Date anoAtual = new Date();
        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        
        String anoAtualConvert = getYearFormat.format(anoAtual);
        
        int getAnoAtualInteiro = Integer.parseInt(anoAtualConvert);
        int idade = getAnoAtualInteiro - anoNascimento;
        
        if(empresaAux.get().getUf().toUpperCase() == "PR" || idade < 18) {
        	throw new FornecedorInvalidoException("Empresa do Paraná, Fornecedor menor de idade");
        }
        // FIM
        
		fornecedorAux.get().getEmpresas().add(empresaAux.get());
		empresaAux.get().getPessoasFisica().add(fornecedorAux.get());
		
		pessoaFisicaDAO.save(fornecedorAux.get());
		empresaDAO.save(empresaAux.get());
	}

}
