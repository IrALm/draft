package com.walsia.api_compta.integrationClient.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Bloque les endpoints authentifiés tant que l'email de l'utilisateur n'est pas
 * vérifié dans Keycloak (claim standard OIDC "email_verified", mis à jour par
 * KeycloakAdminService#marquerEmailVerifie lors de la vérification).
 * Les endpoints publics (création d'entité, auth) ne passent pas par une
 * authentification JWT et ne sont donc pas concernés par ce filtre.
 */
@Component
public class EmailVerifieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            Boolean emailVerifie = jwt.getClaimAsBoolean("email_verified");
            if (emailVerifie == null || !emailVerifie) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write("{\"message\":\"Adresse email non vérifiée\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
