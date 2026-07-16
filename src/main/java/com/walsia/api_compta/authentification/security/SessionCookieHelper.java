package com.walsia.api_compta.authentification.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

/**
 * Cookie de session opaque (BFF) : jamais de JWT côté navigateur. httpOnly +
 * SameSite=Strict + sans Max-Age (cookie de session, invalidé à la fermeture
 * du navigateur - l'autorité réelle reste UserSession.refreshTokenExpiresAt
 * côté serveur). Jamais d'attribut Domain (cookie host-only).
 */
@Component
public class SessionCookieHelper {

    public static final String NOM_COOKIE = "SESSION_ID";

    private final boolean cookieSecure;

    public SessionCookieHelper(@Value("${app.session.cookie-secure}") boolean cookieSecure) {
        this.cookieSecure = cookieSecure;
    }

    public ResponseCookie construireCookieConnexion(String tokenSessionEnClair) {
        return ResponseCookie.from(NOM_COOKIE, tokenSessionEnClair)
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite("Strict")
                .path("/api")
                .build();
    }

    public ResponseCookie construireCookieDeconnexion() {
        return ResponseCookie.from(NOM_COOKIE, "")
                .httpOnly(true)
                .secure(cookieSecure)
                .sameSite("Strict")
                .path("/api")
                .maxAge(0)
                .build();
    }

    public Optional<String> lireToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        return Arrays.stream(cookies)
                .filter(cookie -> NOM_COOKIE.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
    }
}
