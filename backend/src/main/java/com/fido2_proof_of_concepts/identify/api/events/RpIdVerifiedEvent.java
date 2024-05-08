package com.fido2_proof_of_concepts.identify.api.events;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import lombok.Getter;

/**
 * Relying Party id verified event.
 */
@Getter
public class RpIdVerifiedEvent extends AbstractCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       ceremony id for ceremony aggregate
     * @param verified true if received rpId is the same as our rp id.
     */
    public RpIdVerifiedEvent(Identifier id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
