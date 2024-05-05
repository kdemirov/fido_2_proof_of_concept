package com.iwmnetwork.aqtos.internship.identify.model.dto;

import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.AuthenticatorAssertionResponse;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.CollectedClientData;
import com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model.PublicKeyCredentialRequestOptions;
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
    private byte[] credentialId;
    private byte[] publicKey;
}
