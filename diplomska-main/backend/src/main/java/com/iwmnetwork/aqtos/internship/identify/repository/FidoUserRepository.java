package com.iwmnetwork.aqtos.internship.identify.repository;

import com.iwmnetwork.aqtos.internship.identify.model.FidoUser;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.FidoUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FidoUserRepository extends JpaRepository<FidoUser, FidoUserId> {

    /**
     * Finds {@link FidoUser} by given credential id.
     *
     * @param credentialId given credential id
     * @return Optional of {@link FidoUser}
     */
    Optional<FidoUser> findByCredentialId(byte[] credentialId);

    /**
     * Checks if exists fido user by given credential id.
     *
     * @param credentialId given credential id
     * @return true if exists otherwise false
     */
    Boolean existsByCredentialId(byte[] credentialId);
}
