package com.walsia.api_compta.integrationClient.repository;

import com.walsia.api_compta.integrationClient.entity.referentiel.CompteComptable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompteComptableRepository extends JpaRepository<CompteComptable, String> {

    long countByClasseCompteComptable_ReferentielComptable_Id(String referentielComptableId);
}
