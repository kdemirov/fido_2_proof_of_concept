package com.iwmnetwork.aqtos.internship.identify.model.dto;

import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AttestedCredentialData;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AuthenticatorAttestationResponse;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.PublicKeyCredentialCreationOptions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Holder for Registration Ceremony Aggregate In Memory Repository.
 */
@Getter
@Setter
@NoArgsConstructor
public class RegistrationCeremonyInMemory extends CeremonyHolder {
    private AttestedCredentialData attestedCredentialData;
    private AuthenticatorAttestationResponse attestationResponse;
    private PublicKeyCredentialCreationOptions options;
    private PublicKeyCredentialCreationResponse credentialResponse;
}
