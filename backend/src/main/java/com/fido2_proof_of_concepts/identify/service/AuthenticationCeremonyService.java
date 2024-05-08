package com.fido2_proof_of_concepts.identify.service;


import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.PublicKeyCredentialRequestOptions;

/**
 * Authentication ceremony service
 */
public interface AuthenticationCeremonyService {

    /**
     * Creates {@link PublicKeyCredentialRequestOptions}.
     *
     * @return {@link PublicKeyCredentialRequestOptions}
     */
    PublicKeyCredentialRequestOptions createRequestOptions();
}
