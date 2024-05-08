package com.fido2_proof_of_concepts.identify.service;


import com.fido2_proof_of_concepts.identify.model.dto.PublicKeyCredentialCreationResponse;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.PublicKeyCredentialCreationOptions;

/**
 * Registration Ceremony Service.
 */
public interface RegistrationCeremonyService {

    /**
     * Creates {@link PublicKeyCredentialCreationOptions} for user with given username.
     *
     * @param username given username
     * @return {@link PublicKeyCredentialCreationOptions}
     */
    PublicKeyCredentialCreationOptions createOptions(String username);

    /**
     * Checks if the credential id are not registered to existing user
     * and saves fido user if the check is successful.
     * step 22 from <a href="https://www.w3.org/TR/webauthn/#sctn-registering-a-new-credential">...</a>
     *
     * @param response given response
     * @return true or false
     */
    boolean registrationSuccessful(PublicKeyCredentialCreationResponse response);
}
