package com.fido2_proof_of_concepts.identify.api.events;

import com.fido2_proof_of_concepts.common.events.AbstractEvent;
import com.fido2_proof_of_concepts.identify.model.identifiers.FidoUserId;
import com.fido2_proof_of_concepts.identify.model.identifiers.UserId;
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
