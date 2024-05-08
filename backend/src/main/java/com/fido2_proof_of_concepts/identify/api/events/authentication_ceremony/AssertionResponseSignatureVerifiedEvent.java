package com.fido2_proof_of_concepts.identify.api.events.authentication_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.events.AbstractCeremonyEvent;
import lombok.Getter;

/**
 * Authenticator Assertion Response signature verified event.
 */
@Getter
public class AssertionResponseSignatureVerifiedEvent extends AbstractCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param ceremonyId authentication ceremony id for authentication ceremony aggregate
     * @param verified   true if signature if valid otherwise false
     */
    public AssertionResponseSignatureVerifiedEvent(Identifier ceremonyId, boolean verified) {
        super(ceremonyId);
        this.verified = verified;
    }
}
