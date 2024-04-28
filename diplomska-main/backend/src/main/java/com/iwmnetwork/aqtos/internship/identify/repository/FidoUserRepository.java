package com.iwmnetwork.aqtos.internship.identify.repository;

import com.iwmnetwork.aqtos.internship.identify.model.FidoUser;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.FidoUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FidoUserRepository extends JpaRepository<FidoUser, FidoUserId> {
    Optional<FidoUser> findByCredentialId(byte[] credentialId);
}
