package com.iwmnetwork.aqtos.identify.demo.repository;

import com.iwmnetwork.aqtos.identify.demo.model.aggregate.RelyingParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelyingPartyJpaRepository extends JpaRepository<RelyingParty, String> {
}
