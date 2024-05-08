package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Parent class for Authenticator response {@link AuthenticatorAttestationResponse}
 * and {@link AuthenticatorAssertionResponse}.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AuthenticatorResponse {
    private byte[] clientDataJSON;
}
