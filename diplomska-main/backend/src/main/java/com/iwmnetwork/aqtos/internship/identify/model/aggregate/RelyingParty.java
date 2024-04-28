package com.iwmnetwork.aqtos.internship.identify.model.aggregate;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class RelyingParty {
    private String id = "localhost";

    @Id
    private String challenge;


    public RelyingParty(String challenge) {
        this.challenge = challenge;
    }

    public RelyingParty() {
    }
}
