package com.iwmnetwork.aqtos.identify.demo.model;

import com.iwmnetwork.aqtos.identify.demo.model.identifiers.FidoUserId;
import lombok.Getter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Getter
public class FidoUser {
    @EmbeddedId
    private FidoUserId fidoUserId;
    private byte[] credentialId;
    private byte[] publicKey;

    public FidoUser(FidoUserId id, byte[] credentialId, byte[] publicKey) {
        this.fidoUserId = id;
        this.credentialId = credentialId;
        this.publicKey = publicKey;
    }

    public FidoUser() {
    }


}
