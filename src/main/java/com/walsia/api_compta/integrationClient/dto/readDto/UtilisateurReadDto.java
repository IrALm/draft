package com.walsia.api_compta.integrationClient.dto.readDto;

import com.walsia.api_compta.integrationClient.entity.utilisateur.Role;

public record UtilisateurReadDto(
        String id,
        String nom,
        String email,
        Role role,
        boolean actif,
        boolean emailVerifie,
        String entiteId
) {
}
