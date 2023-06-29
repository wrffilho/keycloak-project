package com.example.demo.keycloak;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class KeycloakHelper {

	public static KeycloakSecurityContext getKeycloakSecurityContext() {

		HttpServletRequest req = getRequest();

		return (KeycloakSecurityContext) req.getAttribute(KeycloakSecurityContext.class.getName());

	}

	public static AccessToken getAccessToken() {
		return getKeycloakSecurityContext().getToken();
	}

	public static String getTokenString() {
		return getKeycloakSecurityContext().getTokenString();
	}

	private static HttpServletRequest getRequest() {
		ServletRequestAttributes reqAttrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

		return reqAttrs.getRequest();
	}

}
