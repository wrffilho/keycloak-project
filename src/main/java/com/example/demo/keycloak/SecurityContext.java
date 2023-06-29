package com.example.demo.keycloak;

public interface SecurityContext {

	public boolean isAtivo();

	public Long getIdOrgaoAutuador();

	public boolean isUsuarioOrgaoEstadual();

	public boolean isInativo();

	public boolean hasRole(String role);

	public boolean isAdminOrgao();

	public boolean isAdmin();

}
