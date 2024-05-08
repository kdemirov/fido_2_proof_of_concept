package com.fido2_proof_of_concepts.identify.api.events.authentication_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.events.AbstractCeremonyEvent;
import lombok.Getter;

/**
 * Value of stored signature count verified event
 */
@Getter
public class ValueOfStoredSignatureCountVerifiedEvent extends AbstractCeremonyEvent {

    private final boolean verified;
    private final int signatureCount;

    /**
     * Constructor.
     *
     * @param ceremonyId     authentication ceremony id for authentication ceremony aggregate
     * @param verified       verified if the received signature counter in auth data is greater that the one stored in fido user
     * @param signatureCount new signature counter value for fido user
     */
    public ValueOfStoredSignatureCountVerifiedEvent(Identifier ceremonyId,
                                                    boolean verified,
                                                    int signatureCount) {
        super(ceremonyId);
        this.verified = verified;
        this.signatureCount = signatureCount;
    }
}
