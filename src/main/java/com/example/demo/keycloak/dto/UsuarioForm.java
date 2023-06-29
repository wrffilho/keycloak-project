package com.example.demo.keycloak.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.keycloak.representations.idm.RoleRepresentation;

public class UsuarioForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@Email
	private String email;

	@NotNull
	private String nome;

	@NotNull
	private String matricula;

	private String cpf;

	@NotNull
	private String telefone;

	@NotNull
	private List<RoleRepresentation> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public List<RoleRepresentation> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleRepresentation> roles) {
		this.roles = roles;
	}

}
