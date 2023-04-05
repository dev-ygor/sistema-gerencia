package com.gerencia.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Table(name = "fornecedor_pessoa_fisica")
public class PessoaFisica extends Fornecedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 11)
	@NotBlank
	@Size(min = 11, max = 11, message = "O CNPJ contém no mínimo 11 caracteres.")
	private String cpf;
	
	@Column(length = 8)
	@Size(min = 8, max = 8, message = "O RG contém no mínimo 8 caracteres.")
	private String rg;
	
	@NotBlank
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date nascimento;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "pessoasFisica")
	@JsonIgnoreProperties("pessoasFisica")
	Set<Empresa> empresas = new HashSet<>();

	public PessoaFisica() {
	}
	
	public PessoaFisica(String nome, String email, String cep, String logradouro, String complemento, String bairro,
			String cidade, String uf, Long id, String cpf, String rg, Date nascimento) {
		super(nome, email, cep, logradouro, complemento, bairro, cidade, uf);
		this.id = id;
		this.cpf = cpf;
		this.rg = rg;
		this.nascimento = nascimento;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public Long getId() {
		return id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Set<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(Set<Empresa> empresas) {
		this.empresas = empresas;
	}
	
}
