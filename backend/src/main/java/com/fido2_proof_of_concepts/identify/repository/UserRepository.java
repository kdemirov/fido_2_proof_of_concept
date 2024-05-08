package com.fido2_proof_of_concepts.identify.repository;

import com.fido2_proof_of_concepts.identify.model.FidoUser;
import com.fido2_proof_of_concepts.identify.model.aggregate.User;
import com.fido2_proof_of_concepts.identify.model.identifiers.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Jpa repository for {@link User}.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UserId> {

    /**
     * Finds a user by given username.
     *
     * @param username given username
     * @return optional of {@link User}
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a user by given fido user.
     *
     * @param fidoUser given fido user
     * @return optional of {@link User}
     */
    Optional<User> findByFidoUser(FidoUser fidoUser);
}
