package com.fido2_proof_of_concepts.identify.model.dto;

import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.AttestedCredentialData;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.AuthenticatorAttestationResponse;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.PublicKeyCredentialCreationOptions;
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
