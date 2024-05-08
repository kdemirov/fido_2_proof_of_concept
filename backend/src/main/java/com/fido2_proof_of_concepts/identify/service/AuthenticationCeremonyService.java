package com.iwmnetwork.aqtos.internship.identify.service;

import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.PublicKeyCredentialRequestOptions;

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
