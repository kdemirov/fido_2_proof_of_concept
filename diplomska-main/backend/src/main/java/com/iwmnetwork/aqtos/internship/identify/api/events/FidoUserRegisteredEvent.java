package com.iwmnetwork.aqtos.internship.identify.api.events;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.FidoUserId;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.UserId;
import lombok.Data;

@Data
public class FidoUserRegisteredEvent extends AbstractEvent {
    private UserId userId;
    private FidoUserId fidoUserId;
    private String username;
    private byte[] publicKey;
    private byte[] credentialId;
    private int signCount;

    public FidoUserRegisteredEvent(UserId userId,
                                   FidoUserId fidoUserId,
                                   String username,
                                   byte[] publicKey,
                                   byte[] credentialId,
                                   int signCount) {
        this.userId = userId;
        this.fidoUserId = fidoUserId;
        this.username = username;
        this.credentialId = credentialId;
        this.publicKey = publicKey;
        this.signCount = signCount;
    }

    public FidoUserRegisteredEvent() {
    }
}
