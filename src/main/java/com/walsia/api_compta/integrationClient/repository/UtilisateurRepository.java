package com.walsia.api_compta.integrationClient.repository;

import com.walsia.api_compta.integrationClient.entity.utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {

    Optional<Utilisateur> findByEmail(String email);

    boolean existsByEmail(String email);
}
