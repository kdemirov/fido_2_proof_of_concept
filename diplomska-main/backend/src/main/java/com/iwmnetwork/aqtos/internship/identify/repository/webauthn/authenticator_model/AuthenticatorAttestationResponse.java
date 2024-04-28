/*
The AuthenticatorAttestationResponse interface
represents the authenticator's response
to a client’s request for the creation of a new public key
credential. It contains information about the new credential
that can be used to identify it for later use, and metadata
that can be used by the WebAuthn Relying Party to assess the characteristics of the credential
during registration
more information you can find here ...
https://w3c.github.io/webauthn/#authenticatorattestationresponse
* */
package com.iwmnetwork.aqtos.internship.identify.repository.webauthn.authenticator_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticatorAttestationResponse extends AuthenticatorResponse {
    private int alg;
    private byte[] sig;
    private byte[] x5c;
    private byte[] authData;
    private String fmt;


}
