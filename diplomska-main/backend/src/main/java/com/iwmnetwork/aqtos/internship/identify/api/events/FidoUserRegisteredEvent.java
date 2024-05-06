package com.iwmnetwork.aqtos.internship.identify.api.events;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.FidoUserId;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.UserId;
import lombok.Data;
import lombok.Getter;

/**
 * Fido user registered event.
 */
@Getter
public class FidoUserRegisteredEvent extends AbstractEvent {

    private UserId userId;
    private FidoUserId fidoUserId;
    private String username;
    private byte[] publicKey;
    private byte[] credentialId;
    private int signCount;

    /**
     * @param userId       user id
     * @param fidoUserId   fido user id
     * @param username     username
     * @param publicKey    public key
     * @param credentialId credential id
     * @param signCount    signature counter
     */
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
}
