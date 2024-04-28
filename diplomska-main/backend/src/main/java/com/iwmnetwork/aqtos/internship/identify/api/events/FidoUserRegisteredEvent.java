package com.iwmnetwork.aqtos.internship.identify.api.events;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.FidoUserId;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.UserId;
import lombok.Data;

@Data
public class FidoUserRegisteredEvent extends AbstractEvent{
    private UserId userId;
    private FidoUserId fidoUserId;
    private String username;
    private byte[] publicKey;
    private byte[] credentialId;

    public FidoUserRegisteredEvent(UserId userId,
                                   FidoUserId fidoUserId,
                                   String username,
                                   byte[] publicKey,
                                   byte[] credentialId) {
        this.userId = userId;
        this.fidoUserId=fidoUserId;
        this.username = username;
        this.credentialId=credentialId;
        this.publicKey=publicKey;
    }

    public FidoUserRegisteredEvent() {
    }
}
