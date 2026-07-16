package com.walsia.api_compta.integrationClient.entity.utilisateur;

import com.walsia.api_compta.integrationClient.entity.entite.Entite;
import jakarta.persistence.*;
import lombok.*;

/**
 * Utilisateur de l'application, rattaché à une Entite (PME, ONG...).
 * Le rôle détermine les droits d'accès aux modules administratif et financier.
 */
@Entity
@Table(name = "utilisateur", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    private boolean actif = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entite_id", nullable = false)
    private Entite entite;
}
