package com.fido2_proof_of_concepts.identify.api.events.registration_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.events.AbstractCeremonyEvent;
import lombok.Getter;

/**
 * Alg in auth data verified event.
 */
@Getter
public class AlgParameterInAuthDataVerifiedEvent extends AbstractCeremonyEvent {

    private final boolean verified;

    /**
     * Constructor.
     *
     * @param id       registration ceremony id for registration ceremony aggregate.
     * @param verified true if alg matches with the one we send in CreatePublicKeyCredentialsOptions
     */
    public AlgParameterInAuthDataVerifiedEvent(Identifier id, boolean verified) {
        super(id);
        this.verified = verified;
    }
}
