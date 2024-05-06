package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractCeremonyEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AttestedCredentialData;
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
