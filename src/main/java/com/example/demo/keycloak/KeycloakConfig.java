package com.example.demo.keycloak;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

import java.lang.reflect.Field;

import org.keycloak.AuthorizationContext;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.authorization.client.ClientAuthorizationContext;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.adapters.config.PolicyEnforcerConfig.PathConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class KeycloakConfig {

	@Bean
	@Scope(scopeName = SCOPE_REQUEST, proxyMode = TARGET_CLASS)
	public AccessToken getAccessToken() {
		return KeycloakHelper.getAccessToken();
	}

	@Bean
	@Scope(scopeName = SCOPE_REQUEST, proxyMode = TARGET_CLASS)
	public KeycloakSecurityContext getKeycloakSecurityContext() {
		return KeycloakHelper.getKeycloakSecurityContext();
	}

	@Bean
	@Scope(scopeName = SCOPE_REQUEST, proxyMode = TARGET_CLASS)
	public PathConfig getPathConfig() {

		try {

			AuthorizationContext authorizationContext = getKeycloakSecurityContext().getAuthorizationContext();

			Class<?> clazz = authorizationContext.getClass();

			if (authorizationContext instanceof ClientAuthorizationContext) {
				clazz = authorizationContext.getClass().getSuperclass();
			}

			Field pathField = clazz.getDeclaredField("current");
			pathField.setAccessible(true);

			return (PathConfig) pathField.get(authorizationContext);

		} catch (Exception e) {
			throw new RuntimeException("Não Foi possível recuperar o current path");
		}
	}

	
}
