package com.iwmnetwork.aqtos.identify.demo.repository;

import com.iwmnetwork.aqtos.identify.demo.model.FidoUser;
import com.iwmnetwork.aqtos.identify.demo.model.identifiers.FidoUserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FidoUserRepository extends JpaRepository<FidoUser, FidoUserId> {
    Optional<FidoUser> findByCredentialId(byte[] credentialId);
}
