package com.walsia.api_compta.integrationClient.specification;

import com.walsia.api_compta.integrationClient.entity.referentiel.ClasseCompteComptable;
import jakarta.persistence.criteria.Expression;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaExpression;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ClasseCompteComptableSpecification {

    /** referentielComptableId : frontière de sécurité (isolation tenant), toujours appliqué, jamais un critère optionnel. */
    public Specification<ClasseCompteComptable> build(String referentielComptableId, String q) {
        return Specification
                .where(parReferentiel(referentielComptableId))
                .and(parRecherche(q));
    }

    private Specification<ClasseCompteComptable> parReferentiel(String referentielComptableId) {
        return (root, query, cb) -> cb.equal(root.get("referentielComptable").get("id"), referentielComptableId);
    }

    private Specification<ClasseCompteComptable> parRecherche(String q) {
        return (root, query, cb) -> {
            if (q == null || q.isBlank()) {
                return null;
            }
            // .as(String.class) ne suffit pas ici : sur un Path<Integer>, il ne fait que
            // retyper l'expression côté Java, sans émettre de CAST SQL - Postgres refuse
            // alors "numero LIKE ?" (integer ~~ text). Il faut un vrai cast SQL explicite.
            @SuppressWarnings({"unchecked", "rawtypes"})
            JpaExpression<Integer> numero = (JpaExpression<Integer>) (JpaExpression) root.get("numero");
            Expression<String> numeroTexte = ((HibernateCriteriaBuilder) cb).cast(numero, String.class);
            Expression<String> titreNormalise = cb.function("unaccent", String.class, cb.lower(root.get("titre")));
            String motif = "%" + RechercheTexteUtils.normaliser(q) + "%";
            return cb.or(
                    cb.like(numeroTexte, "%" + q.trim() + "%"),
                    cb.like(titreNormalise, motif));
        };
    }
}
