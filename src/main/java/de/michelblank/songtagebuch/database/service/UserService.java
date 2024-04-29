package de.michelblank.songtagebuch.database.service;

import de.michelblank.songtagebuch.database.entity.User;
import de.michelblank.songtagebuch.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findById(final UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByIdentityProviderAndIdpId(final String identityProvider, final String idpId) {
        return userRepository.findByIdentityProviderAndIdpId(identityProvider, idpId);
    }

    public User putUser(final User user) {
        return userRepository.save(user);
    }
}
