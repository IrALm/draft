package com.walsia.api_compta.integrationClient.dto.readDto;

/** Projection légère d'un compte, pour alimenter un select (ex. « Compte parent » du formulaire de création). */
public record CompteOptionReadDto(
        String id,
        String numero,
        String libelle
) {
}
