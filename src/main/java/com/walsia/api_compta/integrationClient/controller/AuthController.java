package com.walsia.api_compta.integrationClient.controller;

import com.walsia.api_compta.integrationClient.dto.formDto.ForgotPasswordForm;
import com.walsia.api_compta.integrationClient.dto.formDto.ResendVerificationForm;
import com.walsia.api_compta.integrationClient.dto.formDto.ResetPasswordForm;
import com.walsia.api_compta.integrationClient.service.interfaces.AuthAccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthAccountService authAccountService;

    public AuthController(AuthAccountService authAccountService) {
        this.authAccountService = authAccountService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> demanderReinitialisation(@Valid @RequestBody ForgotPasswordForm form) {
        authAccountService.demanderReinitialisationMotDePasse(form.email());
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> reinitialiserMotDePasse(@Valid @RequestBody ResetPasswordForm form) {
        authAccountService.reinitialiserMotDePasse(form.token(), form.nouveauMotDePasse());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verify-email")
    public ResponseEntity<Void> verifierEmail(@RequestParam String token) {
        authAccountService.verifierEmail(token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<Void> renvoyerVerification(@Valid @RequestBody ResendVerificationForm form) {
        authAccountService.renvoyerEmailVerification(form.email());
        return ResponseEntity.accepted().build();
    }
}
