package com.fido2_proof_of_concepts.identify.api.events.registration_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.api.events.AbstractCeremonyEvent;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.AttestedCredentialData;
import lombok.Getter;

/**
 * Cbor decoding performed over Attestation Response Object Event.
 */
@Getter
public class CborDecodingPerformedOnResponseObjectEvent extends AbstractCeremonyEvent {

    private final AttestedCredentialData attestedCredentialData;

    /**
     * Constructor.
     *
     * @param id             registration ceremony id for registration ceremony aggregate
     * @param credentialData decoded attested credential data
     */
    public CborDecodingPerformedOnResponseObjectEvent(Identifier id,
                                                      AttestedCredentialData credentialData) {
        super(id);
        this.attestedCredentialData = credentialData;
    }
}
