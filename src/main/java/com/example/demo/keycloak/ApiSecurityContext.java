package com.example.demo.keycloak;

import java.net.URI;
import java.util.Arrays;
import java.util.Set;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.keycloak.dto.UsuarioForm;

@Component
public class ApiSecurityContext implements SecurityContext {

	@Autowired
	private AccessToken accessToken;

	@Autowired
	private Keycloak keycloakAdmin;

	@Value("${keycloak.realm}")
	private String realmApp;

	@Value("${keycloak.resource}")
	private String clientId;

	public Set<String> getRoles() {
		return accessToken.getResourceAccess(clientId).getRoles();
	}

	public boolean hasRole(String role) {
		return getRoles().contains(role);
	}

	public boolean createAccount(UsuarioForm usuario) {
		String userId = createUser(usuario);

		createCredential(userId);
		addUserRole(userId);

		return true;

	}

	private void addUserRole(String userId) {
		RoleRepresentation realmRole = findUserRole();

		keycloakAdmin.realms().realm(realmApp).users().get(userId).roles().realmLevel().add(Arrays.asList(realmRole));
	}

	private RoleRepresentation findUserRole() {
		return keycloakAdmin.realms().realm(realmApp).roles().get("user").toRepresentation();
	}

	private void createCredential(String userId) {
		CredentialRepresentation rep = new CredentialRepresentation();
		rep.setType(CredentialRepresentation.PASSWORD);
		rep.setValue("123");
		rep.setTemporary(false);

		UserResource userResource = keycloakAdmin.realms().realm(realmApp).users().get(userId);

		userResource.resetPassword(rep);
		userResource.executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));

	}

	private String createUser(UsuarioForm usuario) {
		UserRepresentation userRepresentation = new UserRepresentation();
		userRepresentation.setUsername(usuario.getCpf());
		userRepresentation.setEmail(usuario.getEmail());
		userRepresentation.setEnabled(Boolean.TRUE);

		// userRepresentation.setFirstName(usuario.getNome().split(" ")[0]);
		userRepresentation.setRequiredActions(Arrays.asList("UPDATE_PASSWORD"));

		Response response = keycloakAdmin.realms().realm(realmApp).users().create(userRepresentation);

		String userId = getCreatedId(response);

		response.close();
		return userId;
	}

	public static String getCreatedId(Response response) {
		URI location = response.getLocation();
		if (!response.getStatusInfo().equals(Response.Status.CREATED)) {
			Response.StatusType statusInfo = response.getStatusInfo();
			throw new WebApplicationException("Create method returned status " + statusInfo.getReasonPhrase()
					+ " (Code: " + statusInfo.getStatusCode() + "); expected status: Created (201)", response);
		}
		if (location == null) {
			return null;
		}
		String path = location.getPath();
		return path.substring(path.lastIndexOf('/') + 1);
	}

	@Override
	public boolean isAtivo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Long getIdOrgaoAutuador() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInativo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAdminOrgao() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAdmin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUsuarioOrgaoEstadual() {
		// TODO Auto-generated method stub
		return false;
	}

}
