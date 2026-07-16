package com.walsia.api_compta.integrationClient.controller;

import com.walsia.api_compta.integrationClient.dto.formDto.EntiteCreationForm;
import com.walsia.api_compta.integrationClient.dto.readDto.EntiteCreeeReadDto;
import com.walsia.api_compta.integrationClient.service.interfaces.EntiteCreationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/entites")
public class EntiteController {

    private final EntiteCreationService entiteCreationService;

    public EntiteController(EntiteCreationService entiteCreationService) {
        this.entiteCreationService = entiteCreationService;
    }

    @PostMapping
    public ResponseEntity<EntiteCreeeReadDto> creerEntite(@Valid @RequestBody EntiteCreationForm form) {
        EntiteCreeeReadDto reponse = entiteCreationService.creerEntiteEtAdmin(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(reponse);
    }
}
