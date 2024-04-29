package de.michelblank.songtagebuch.database.repository;

import de.michelblank.songtagebuch.database.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByIdentityProviderAndIdpId(final String identityProvider, final String idpId);
}
