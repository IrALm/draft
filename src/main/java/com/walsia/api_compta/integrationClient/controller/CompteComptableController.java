package com.walsia.api_compta.integrationClient.controller;

import com.walsia.api_compta.integrationClient.dto.formDto.CompteComptableCreationForm;
import com.walsia.api_compta.integrationClient.dto.readDto.CompteComptableExisteReadDto;
import com.walsia.api_compta.integrationClient.dto.readDto.CompteComptableReadDto;
import com.walsia.api_compta.integrationClient.dto.readDto.CompteOptionReadDto;
import com.walsia.api_compta.integrationClient.service.interfaces.CompteComptableCreationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompteComptableController {

    private final CompteComptableCreationService compteComptableCreationService;

    public CompteComptableController(CompteComptableCreationService compteComptableCreationService) {
        this.compteComptableCreationService = compteComptableCreationService;
    }

    /** Alimente le select « Compte parent » du formulaire de création (numero + libelle uniquement). */
    @GetMapping("/api/classes/{classeId}/comptes")
    public ResponseEntity<List<CompteOptionReadDto>> listerComptesDeClasse(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable String classeId) {
        return ResponseEntity.ok(compteComptableCreationService.listerComptesDeClasse(jwt.getSubject(), classeId));
    }

    @PostMapping("/api/comptes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COMPTABLE')")
    public ResponseEntity<CompteComptableReadDto> creer(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CompteComptableCreationForm form) {
        CompteComptableReadDto reponse = compteComptableCreationService.creerCompte(jwt.getSubject(), form);
        return ResponseEntity.status(HttpStatus.CREATED).body(reponse);
    }

    @GetMapping("/api/comptes/exists")
    public ResponseEntity<CompteComptableExisteReadDto> existe(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam String numero) {
        boolean existe = compteComptableCreationService.numeroExiste(jwt.getSubject(), numero);
        return ResponseEntity.ok(new CompteComptableExisteReadDto(existe));
    }
}
