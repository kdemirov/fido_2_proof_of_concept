package com.iwmnetwork.aqtos.internship.identify.api.events.registration_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractCeremonyEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.RegistrationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AuthenticatorAttestationResponse;
import lombok.Getter;

/**
 * Decoded Attestation Response Event
 */
@Getter
public class DecodedAttestationResponseEvent extends AbstractCeremonyEvent {

    private final AuthenticatorAttestationResponse attestationResponse;
    private final String clientDataJson;

    /**
     * Constructor.
     *
     * @param id             registration ceremony id for registration ceremony aggregate
     * @param response       decoded AuthenticatorAttestationResponse
     * @param clientDataJson client data json text
     */
    public DecodedAttestationResponseEvent(Identifier id,
                                           AuthenticatorAttestationResponse response,
                                           String clientDataJson) {
        super(id);
        this.attestationResponse = response;
        this.clientDataJson = clientDataJson;
    }
}
