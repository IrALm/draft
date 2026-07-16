package com.walsia.api_compta.integrationClient.service.impl;

import com.walsia.api_compta.integrationClient.entity.utilisateur.UserTokenType;
import com.walsia.api_compta.integrationClient.entity.utilisateur.Utilisateur;
import com.walsia.api_compta.integrationClient.exception.ConflitException;
import com.walsia.api_compta.integrationClient.exception.RessourceIntrouvableException;
import com.walsia.api_compta.integrationClient.repository.UtilisateurRepository;
import com.walsia.api_compta.integrationClient.service.interfaces.AuthAccountService;
import com.walsia.api_compta.integrationClient.service.interfaces.KeycloakAdminService;
import com.walsia.api_compta.integrationClient.service.interfaces.MailService;
import com.walsia.api_compta.integrationClient.service.interfaces.UserTokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthAccountServiceImpl implements AuthAccountService {

    private final UtilisateurRepository utilisateurRepository;
    private final KeycloakAdminService keycloakAdminService;
    private final MailService mailService;
    private final UserTokenService userTokenService;

    public AuthAccountServiceImpl(
            UtilisateurRepository utilisateurRepository,
            KeycloakAdminService keycloakAdminService,
            MailService mailService,
            UserTokenService userTokenService) {
        this.utilisateurRepository = utilisateurRepository;
        this.keycloakAdminService = keycloakAdminService;
        this.mailService = mailService;
        this.userTokenService = userTokenService;
    }

    @Override
    public void demanderReinitialisationMotDePasse(String email) {
        // Silencieux si l'email est inconnu : évite de révéler l'existence d'un compte.
        utilisateurRepository.findByEmail(email).ifPresent(utilisateur -> {
            String token = userTokenService.genererToken(utilisateur, UserTokenType.PASSWORD_RESET);
            mailService.envoyerReinitialisationMotDePasse(utilisateur.getEmail(), utilisateur.getNom(), token);
        });
    }

    @Override
    @Transactional
    public void reinitialiserMotDePasse(String token, String nouveauMotDePasse) {
        Utilisateur utilisateur = userTokenService.consommerToken(token, UserTokenType.PASSWORD_RESET);
        keycloakAdminService.reinitialiserMotDePasse(utilisateur.getKeycloakId(), nouveauMotDePasse);
    }

    @Override
    @Transactional
    public void verifierEmail(String token) {
        Utilisateur utilisateur = userTokenService.consommerToken(token, UserTokenType.EMAIL_VERIFICATION);
        utilisateur.setEmailVerifie(true);
        utilisateurRepository.save(utilisateur);
        keycloakAdminService.marquerEmailVerifie(utilisateur.getKeycloakId(), true);
    }

    @Override
    public void renvoyerEmailVerification(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur introuvable : " + email));
        if (utilisateur.isEmailVerifie()) {
            throw new ConflitException("Cet email est déjà vérifié");
        }
        String token = userTokenService.genererToken(utilisateur, UserTokenType.EMAIL_VERIFICATION);
        mailService.envoyerEmailVerification(utilisateur.getEmail(), utilisateur.getNom(), token);
    }
}
