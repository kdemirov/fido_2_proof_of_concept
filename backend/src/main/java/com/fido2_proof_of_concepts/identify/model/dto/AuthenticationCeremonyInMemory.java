package com.fido2_proof_of_concepts.identify.model.dto;

import com.fido2_proof_of_concepts.identify.model.FidoUser;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.AuthenticatorAssertionResponse;
import com.fido2_proof_of_concepts.identify.repository.webauthn.authenticator_model.PublicKeyCredentialRequestOptions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Holder for authentication in memory repository.
 */
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationCeremonyInMemory extends CeremonyHolder {
    private PublicKeyCredentialRequestOptions options;
    private AuthenticatorAssertionResponse response;
    private FidoUser fidoUser;
    byte[] credentialId;
}
