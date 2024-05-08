package com.fido2_proof_of_concepts.identify.api.events;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import lombok.Getter;

/**
 * RP id hash matches with our RP id hash event
 */
@Getter
public class RpIdHashInAuthDataVerifiedEvent extends AbstractCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param ceremonyId ceremony id for ceremony aggregate
     * @param verified               true if received rpId hash from auth data matches our rpId hash
     */
    public RpIdHashInAuthDataVerifiedEvent(Identifier ceremonyId, boolean verified) {
        super(ceremonyId);
        this.verified = verified;
    }
}
