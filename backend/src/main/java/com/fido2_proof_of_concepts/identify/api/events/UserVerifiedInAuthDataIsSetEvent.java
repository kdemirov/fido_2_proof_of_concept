package com.fido2_proof_of_concepts.identify.api.events;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import lombok.Getter;

/**
 * User verified flag is set in auth data flags event.
 */
@Getter
public class UserVerifiedInAuthDataIsSetEvent extends AbstractCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       ceremony id for ceremony aggregate
     * @param verified true if the second bit in flags is set
     */
    public UserVerifiedInAuthDataIsSetEvent(Identifier id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
