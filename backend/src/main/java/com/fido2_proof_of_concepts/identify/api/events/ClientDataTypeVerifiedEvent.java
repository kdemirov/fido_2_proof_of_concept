package com.fido2_proof_of_concepts.identify.api.events;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import lombok.Getter;

/**
 * Client data type verified for both ceremonies' event.
 */
@Getter
public class ClientDataTypeVerifiedEvent extends AbstractCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       ceremony id for ceremony aggregate.
     * @param verified true if client data is webauthn.create or webauthn.get.
     */
    public ClientDataTypeVerifiedEvent(Identifier id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
