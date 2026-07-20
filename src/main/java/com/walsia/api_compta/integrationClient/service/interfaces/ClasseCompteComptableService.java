package com.walsia.api_compta.integrationClient.service.interfaces;

import com.walsia.api_compta.integrationClient.dto.formDto.CompteComptableSearchForm;
import com.walsia.api_compta.integrationClient.dto.readDto.ClasseCompteComptableReadDto;
import com.walsia.api_compta.integrationClient.dto.readDto.CompteComptablePageReadDto;

import java.util.List;

public interface ClasseCompteComptableService {

    List<ClasseCompteComptableReadDto> listerClasses(String keycloakIdAppelant, String q);

    CompteComptablePageReadDto rechercherComptes(String keycloakIdAppelant, String classeId, CompteComptableSearchForm form);
}
