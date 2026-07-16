package com.walsia.api_compta.integrationClient.entity.referentiel;

import com.walsia.api_compta.integrationClient.entity.entite.Entite;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Instance du plan de comptes pour une Entite donnée, dérivée d'un
 * ReferentielComptable. Permet à une PME et une ONG d'avoir chacune
 * leur propre jeu de comptes, tout en partageant la même structure
 * normative de référence.
 */
@Entity
@Table(name = "plan_comptable")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanComptable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "referentiel_comptable_id", nullable = false)
    private ReferentielComptable referentielComptable;

    private LocalDate dateMiseEnPlace;

    private boolean actif = true;

    @Builder.Default
    @OneToMany(mappedBy = "planComptable", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompteComptable> comptes = new ArrayList<>();
}
