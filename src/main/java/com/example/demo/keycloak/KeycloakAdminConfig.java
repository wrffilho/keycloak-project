package com.example.demo.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.keycloak.adapters.springboot.KeycloakSpringBootProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.token.TokenService;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@org.springframework.context.annotation.Configuration
public class KeycloakAdminConfig {

	@Value("${keycloak.auth-server-url}")
	private String serverUrl;

	@Value("${keycloak.realm}")
	private String realm;

	@Bean
	@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Keycloak getKeycloakAdmin() {

		return KeycloakBuilder.builder().serverUrl(serverUrl).realm(realm).grantType(null).clientId("admin-cli")
				.authorization(KeycloakHelper.getTokenString()).build();

	}

	@Bean
	public AuthzClient authzClient(KeycloakSpringBootProperties kcProperties) {
		Configuration configuration = new Configuration(kcProperties.getAuthServerUrl(), kcProperties.getRealm(),
				kcProperties.getResource(), kcProperties.getCredentials(), null);

		return AuthzClient.create(configuration);
	}

	@Bean
	@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public TokenService tokenService() {

		ResteasyClient client = new ResteasyClientBuilder().connectionPoolSize(10).build();

		ResteasyWebTarget target = client.target(serverUrl);

		TokenService tokenService = target.proxy(TokenService.class);

		return tokenService;

	}

}