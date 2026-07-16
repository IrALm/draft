package com.walsia.api_compta.integrationClient.repository;

import com.walsia.api_compta.integrationClient.entity.utilisateur.UserToken;
import com.walsia.api_compta.integrationClient.entity.utilisateur.UserTokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken, String> {

    Optional<UserToken> findByTokenHashAndType(String tokenHash, UserTokenType type);
}
