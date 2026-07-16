package com.walsia.api_compta.integrationClient.dto.formDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordForm(
        @NotBlank String token,
        @NotBlank @Size(min = 8, max = 100) String nouveauMotDePasse
) {
}
