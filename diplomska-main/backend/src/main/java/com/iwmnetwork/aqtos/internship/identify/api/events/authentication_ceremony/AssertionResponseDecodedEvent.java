package com.iwmnetwork.aqtos.internship.identify.api.events.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.api.events.AbstractCeremonyEvent;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.AuthenticationCeremonyId;
import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AuthenticatorAssertionResponse;
import lombok.Getter;

/**
 * Checking if credential request options response is instance of {@link AuthenticatorAssertionResponse}
 */
@Getter
public class AssertionResponseDecodedEvent extends AbstractCeremonyEvent {

    private final AuthenticatorAssertionResponse response;
    private final String credentialId;
    private final String clientDataJson;

    /**
     * Constructor.
     *
     * @param authenticationCeremonyId authentication ceremony id for authentication ceremony aggregate
     * @param response                 decoded {@link AuthenticatorAssertionResponse}
     * @param credentialId             credential id in json
     */
    public AssertionResponseDecodedEvent(Identifier authenticationCeremonyId,
                                         AuthenticatorAssertionResponse response,
                                         String credentialId,
                                         String clientDataJson) {
        super(authenticationCeremonyId);
        this.response = response;
        this.credentialId = credentialId;
        this.clientDataJson = clientDataJson;
    }
}
