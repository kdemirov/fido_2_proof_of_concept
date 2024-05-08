package com.iwmnetwork.aqtos.internship.identify.repository;

import com.iwmnetwork.aqtos.internship.identify.model.FidoUser;
import com.iwmnetwork.aqtos.internship.identify.model.aggregate.User;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.UserId;
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
