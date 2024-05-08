package com.fido2_proof_of_concepts.identify.api.events.authentication_ceremony;

import com.fido2_proof_of_concepts.common.identifiers.Identifier;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.PublicKeyCredentialRequestOptions;
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
