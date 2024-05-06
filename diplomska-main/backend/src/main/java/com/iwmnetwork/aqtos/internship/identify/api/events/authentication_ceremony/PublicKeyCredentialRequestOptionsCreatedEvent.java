package com.iwmnetwork.aqtos.internship.identify.api.events.authentication_ceremony;

import com.iwmnetwork.aqtos.internship.identify.model.identifiers.Identifier;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.PublicKeyCredentialRequestOptions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Public key credential request options created event.
 */
@RequiredArgsConstructor
@Getter
public class PublicKeyCredentialRequestOptionsCreatedEvent  {
    private final Identifier authenticationCeremonyId;
    private final PublicKeyCredentialRequestOptions options;
}
