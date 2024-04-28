package com.iwmnetwork.aqtos.internship.identify.repository;

import com.iwmnetwork.aqtos.internship.identify.model.aggregate.RelyingParty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelyingPartyJpaRepository extends JpaRepository<RelyingParty, String> {
}
