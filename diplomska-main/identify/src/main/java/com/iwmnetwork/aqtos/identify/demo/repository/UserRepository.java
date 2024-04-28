package com.iwmnetwork.aqtos.identify.demo.repository;

import com.iwmnetwork.aqtos.identify.demo.model.FidoUser;
import com.iwmnetwork.aqtos.identify.demo.model.aggregate.User;
import com.iwmnetwork.aqtos.identify.demo.model.identifiers.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {
    Optional<User> findByUsername(String username);

    Optional<User> findByFidoUser(FidoUser fidoUser);
}
