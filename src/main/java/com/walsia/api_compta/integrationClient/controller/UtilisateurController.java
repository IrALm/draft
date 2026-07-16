package com.walsia.api_compta.integrationClient.controller;

import com.walsia.api_compta.integrationClient.dto.readDto.UtilisateurReadDto;
import com.walsia.api_compta.integrationClient.service.interfaces.UtilisateurConsultationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    private final UtilisateurConsultationService utilisateurConsultationService;

    public UtilisateurController(UtilisateurConsultationService utilisateurConsultationService) {
        this.utilisateurConsultationService = utilisateurConsultationService;
    }

    @GetMapping("/me")
    public ResponseEntity<UtilisateurReadDto> moi(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(utilisateurConsultationService.obtenirProfilConnecte(jwt.getSubject()));
    }
}
