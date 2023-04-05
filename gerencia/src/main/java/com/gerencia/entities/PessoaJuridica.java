package com.gerencia.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "fornecedor_pessoa_juridica")
public class PessoaJuridica extends Fornecedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 14)
	@NotBlank
	@Size(min = 14, max = 14, message = "O CNPJ contém no mínimo 11 caracteres.")
	private String CNPJ;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "pessoasJuridica")
	@JsonIgnoreProperties("pessoasJuridica")
	Set<Empresa> empresas = new HashSet<>();
	
	public PessoaJuridica() {
	}

	public PessoaJuridica(String nome, String email, String cep, String logradouro, String complemento, String bairro,
			String cidade, String uf, Long id, String cNPJ) {
		super(nome, email, cep, logradouro, complemento, bairro, cidade, uf);
		this.id = id;
		CNPJ = cNPJ;
	}

	public Long getId() {
		return id;
	}

	public String getCNPJ() {
		return CNPJ;
	}

	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}

	public Set<Empresa> getEmpresas() {
		return empresas;
	}

	
}
