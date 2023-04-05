package com.gerencia.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "empresa")
public class Empresa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 14)
	@Size(min = 14)
	private String CNPJ;
	
	@NotBlank
	private String NomeFantasia;
	
	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String cidade;
	private String uf;
	
	@ManyToMany
	@JoinTable(name = "empresas_fornecedores_PF",
	           joinColumns = @JoinColumn(name = "empresa_id"),
	           inverseJoinColumns = @JoinColumn(name = "pessoa_fisica_id"))
	Set<PessoaFisica> pessoasFisica = new HashSet<>();
	
	@ManyToMany
	@JoinTable(name = "empresas_fornecedores_PJ",
				joinColumns = @JoinColumn(name = "empresa_id"),
				inverseJoinColumns = @JoinColumn(name = "pessoa_juridica_id"))
	Set<PessoaJuridica> pessoasJuridica = new HashSet<>();
	
	public Empresa() {
	}

	public Empresa(Long id, String cNPJ, String nomeFantasia, String cep, String logradouro,
			String complemento, String bairro, String cidade, String uf) {
		super();
		this.id = id;
		CNPJ = cNPJ;
		NomeFantasia = nomeFantasia;
		this.cep = cep;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCNPJ() {
		return CNPJ;
	}

	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}

	public String getNomeFantasia() {
		return NomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		NomeFantasia = nomeFantasia;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Set<PessoaFisica> getPessoasFisica() {
		return pessoasFisica;
	}

	public void setPessoasFisica(Set<PessoaFisica> pessoasFisica) {
		this.pessoasFisica = pessoasFisica;
	}

	public Set<PessoaJuridica> getPessoasJuridica() {
		return pessoasJuridica;
	}

	public void setPessoasJuridica(Set<PessoaJuridica> pessoasJuridica) {
		this.pessoasJuridica = pessoasJuridica;
	}
	
}
