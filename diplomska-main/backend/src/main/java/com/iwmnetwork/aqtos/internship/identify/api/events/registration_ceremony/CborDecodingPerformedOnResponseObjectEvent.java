package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AttestedCredentialData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


/**
 * Cbor decoding performed over Attestation Response Object Event.
 */
@Getter
public class CborDecodingPerformedOnResponseObjectEvent extends AbstractRegistrationCeremonyEvent {

    private final AttestedCredentialData attestedCredentialData;

    /**
     * Constructor.
     *
     * @param id             registration ceremony id for registration ceremony aggregate
     * @param credentialData decoded attested credential data
     */
    public CborDecodingPerformedOnResponseObjectEvent(RegistrationCeremonyId id,
                                                      AttestedCredentialData credentialData) {
        super(id);
        this.attestedCredentialData = credentialData;
    }
}
