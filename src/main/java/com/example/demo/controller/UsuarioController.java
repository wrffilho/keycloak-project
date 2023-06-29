package com.example.demo.controller;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ServerInfoResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.keycloak.KeycloakHelper;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {
	
	@Autowired
	private Keycloak keycloak;
	@GetMapping
	public String getUsuario() {
		KeycloakSecurityContext ksc = KeycloakHelper.getKeycloakSecurityContext();
		String realm = ksc.getRealm();
		System.out.println(realm);
		
		
		ServerInfoResource serverInfo = keycloak.serverInfo();
		
		System.out.println(serverInfo.toString());
			return "Usuario Created";
	}

	@GetMapping("/usr")
	public String salvar() {

		return "Acessou 2";

	}
}
